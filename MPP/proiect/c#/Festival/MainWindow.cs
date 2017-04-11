using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using System.Windows.Forms;
using Festival.Model;

namespace Festival
{
    public partial class MainWindow : Form
    {
        private Controller.Controller Ctrl;
        private BindingList<ShowData> FilteredBs;

        public MainWindow(Controller.Controller ctrl)
        {
            Ctrl = ctrl;
           
            ctrl.setThreadingContext(SynchronizationContext.Current);  // for async updates
            InitializeComponent();
            Grid.AutoGenerateColumns = false;
            SrcGrid.AutoGenerateColumns = false;
            
            Grid.Columns.Add("ArtistName", "Artist");
            Grid.Columns["ArtistName"].DataPropertyName = "ArtistName";
            Grid.Columns.Add("LocationName", "Location");
            Grid.Columns["LocationName"].DataPropertyName = "LocationName";
            Grid.Columns.Add("Date", "Date");
            Grid.Columns["Date"].DataPropertyName = "StartTime";
            Grid.Columns["Date"].DefaultCellStyle.Format = "dd/MM/yyyy";
            Grid.Columns.Add("Remaining", "Remaining");
            Grid.Columns["Remaining"].DataPropertyName = "RemainingSeats";

            SrcGrid.Columns.Add("ArtistName", "Artist");
            SrcGrid.Columns["ArtistName"].DataPropertyName = "ArtistName";
            SrcGrid.Columns.Add("LocationName", "Location");
            SrcGrid.Columns["LocationName"].DataPropertyName = "LocationName";
            SrcGrid.Columns.Add("StartTime", "Time");
            SrcGrid.Columns["StartTime"].DataPropertyName = "StartTime";
            SrcGrid.Columns["StartTime"].DefaultCellStyle.Format = "hh:mm";
            SrcGrid.Columns.Add("Remaining", "Remaining");
            SrcGrid.Columns["Remaining"].DataPropertyName = "RemainingSeats";

            // populate lists
            var bs = new BindingSource(Ctrl.ShowBindingList, null);
    

            FilteredBs = new BindingList<ShowData>();
            Grid.DataSource = bs;
            SrcGrid.DataSource = FilteredBs;

            bs.ListChanged += (sender, args) =>
            {
                toggleFiltering();
                colorLists();
            };
            SelectionChanged();

        }

        private void toggleFiltering()
        {
            FilteredBs.Clear();
            foreach (ShowData show in Ctrl.ShowBindingList)
            {
                if ((show as ShowData).StartTime.Date == DatePicker.Value.Date)
                    FilteredBs.Add(show);
            }
        }

        private void colorLists()
        {
            foreach (DataGridViewRow row in Grid.Rows)
            {
                var backColor = (row.DataBoundItem as ShowData).RemainingSeats == 0
                    ? Color.Red
                    : Color.White;
                row.Cells["Remaining"].Style.BackColor =
                    backColor;
            }
            foreach (DataGridViewRow row in SrcGrid.Rows)
            {
                row.Cells["Remaining"].Style.BackColor =
                    (row.DataBoundItem as ShowData).RemainingSeats == 0
                        ? Color.Red
                        : Color.White;
            }
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            colorLists();
        }

        private void DatePicker_ValueChanged(object sender, EventArgs e)
        {
            toggleFiltering();
            colorLists();
        }

        private void BuyBtn_Click(object sender, EventArgs e)
        {
            int showId = (SrcGrid.SelectedRows[0].DataBoundItem as ShowData).Id;
            string name = NameText.Text;
            int quantity = (int) QuantitySpinner.Value;

            try
            {
                Ctrl.AddPurchase(showId, name, quantity);
            }
            catch (Exception ex)
            {
                string messageBoxText = ex.Message;
                string caption = "Error";
             
                MessageBox.Show(messageBoxText, caption);
            }
        }

        private void SrcGrid_CellContentClick(object sender, DataGridViewCellEventArgs e)
        {
          
        }

        private void SrcGrid_SelectionChanged(object sender, EventArgs e)
        {
            SelectionChanged();
        }

        private void SelectionChanged()
        {
            if (SrcGrid.SelectedRows.Count != 0)
                QuantitySpinner.Maximum = (SrcGrid.SelectedRows[0].DataBoundItem as ShowData).RemainingSeats;
            BuyBtn.Enabled = SrcGrid.SelectedRows.Count != 0;
        }
    }
}

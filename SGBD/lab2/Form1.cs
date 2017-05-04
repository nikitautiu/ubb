using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Data.SqlClient;
using System.Configuration;


namespace lab2
{
    public partial class Form1 : Form
    {
        SqlConnection conexiune;
        DataSet ds;
        SqlDataAdapter da;

        //Binding sources
        BindingSource bsC;
        BindingSource bsP;
        List<TextBox> textBoxList;
        List<Label> labelList;
        int idx = 0;
        private string parentId;

        public Form1()
        {
            InitializeComponent();
            ConnectionStringSettings conSet = ConfigurationManager.ConnectionStrings["conStr"];
            string connectionString = conSet.ConnectionString;
            conexiune = new SqlConnection(connectionString);

            bsP = new BindingSource();
            bsC = new BindingSource();
            da = new SqlDataAdapter();
            ds = new DataSet();
        }

        public void fillDataSet()
        {
            conexiune.Open();
            
            string selectcmd = ConfigurationManager.AppSettings["ParentSelectCmd"];
            da.SelectCommand = new SqlCommand(selectcmd, conexiune);
            da.Fill(ds, "ParentTable");

            selectcmd = ConfigurationManager.AppSettings["ChildSelectCmd"];
            da.SelectCommand = new SqlCommand(selectcmd, conexiune);
            da.Fill(ds, "ChildTable");
            conexiune.Close();
        }

        public void getData()
        {
            //setare bidingSource
            parentId = ConfigurationManager.AppSettings["idParent"];

            DataRelation relation = new DataRelation("Parent_Child_FK",
                ds.Tables["ParentTable"].Columns[parentId],
                ds.Tables["ChildTable"].Columns[parentId]);
            ds.Relations.Add(relation);

            bsP.DataSource = ds;
            bsP.DataMember = "ParentTable";

            bsC.DataSource = bsP;
            bsC.DataMember = "Parent_Child_FK";
        }

        public void populateDataGridViews()
        {
            parentDataGridView.DataSource = bsP;
            childDataGridView.DataSource = bsC;
            getData();
            childDataGridView.AutoResizeColumns();
            parentDataGridView.AutoGenerateColumns = true;
        }

        private void Form1_Load(object sender, EventArgs e)
        {


            fillDataSet();
            populateDataGridViews();
            setUpTextBoxAndLabels();
        }

        private void setUpTextBoxAndLabels()
        {
            idx = 0;
            textBoxList = new List<TextBox>();
            labelList = new List<Label>();

            foreach (Control item in panel1.Controls.OfType<TextBox>())
            {
                panel1.Controls.Remove(item);
            }
            foreach (Control item in panel1.Controls.OfType<Label>())
            {
                panel1.Controls.Remove(item);
            }
            int columnNr = ds.Tables["ChildTable"].Columns.Count;

            int ind = 0;
            for (int i = 0; i < columnNr; i++)
            {

                if (ds.Tables["ChildTable"].Columns[i].ColumnName != parentId)
                {
                    Label label = new Label();
                    label.Text = ds.Tables["ChildTable"].Columns[i].ColumnName;


                    Point textP = new Point(idx/2 * 120, 44 + 44 * (ind % 2));
                    Point lableP = new Point(idx/2 * 120, 30 + 44 * (ind % 2));
                    label.Location = lableP;
                    label.AutoSize = true;

                    TextBox textBox = new TextBox();
                    textBox.Location = textP;
                    textBoxList.Add(textBox);
                    labelList.Add(label);
                    idx++;

                    panel1.Controls.Add(label);
                    panel1.Controls.Add(textBox);
                    ++ind;
                }
            }

        }

        private void addClick(object sender, EventArgs e)
        {
            //adaugare
            conexiune.Open();
            string value;
            int i;
            int selectedRowindex = parentDataGridView.SelectedCells[0].RowIndex;

            string foreingID = parentDataGridView.Rows[selectedRowindex].Cells[0].Value.ToString();

            string insertCmd = ConfigurationManager.AppSettings["ChildInsertCmd"];
            da.InsertCommand = new SqlCommand(insertCmd, conexiune);

            int nrColumns = ds.Tables["ChildTable"].Columns.Count;

            int ind = 1;
            for (i = 0; i < nrColumns; i++)
            {
                if(ds.Tables["ChildTable"].Columns[i].ColumnName != parentId) { 
                    value = "@value" + (ind).ToString();
                    da.InsertCommand.Parameters.Add(value, ds.Tables["ChildTable"].Columns[i].DataType).Value = textBoxList[ind - 1].Text;

                    ++ind;
                }
            }

            value = value = "@value" + (ind).ToString();
            da.InsertCommand.Parameters.Add(value, SqlDbType.VarChar).Value = foreingID;

            da.InsertCommand.ExecuteNonQuery();

            ds.Tables["ChildTable"].Clear();
            string selectcmd = ConfigurationManager.AppSettings["ChildSelectCmd"];
            da.SelectCommand = new SqlCommand(selectcmd, conexiune);
            da.Fill(ds, "ChildTable");

            conexiune.Close();
        }

        private void updateClick(object sender, EventArgs e)
        {
            if (childDataGridView.SelectedCells.Count > 0)
            {
                conexiune.Open();
                string updateCmd = ConfigurationManager.AppSettings["ChildUpdateCmd"];
                da.UpdateCommand = new SqlCommand(updateCmd, conexiune);

                String id = childDataGridView.CurrentRow.Cells[0].Value.ToString();

                int index = ds.Tables["ChildTable"].Columns.Count;
                int i = 0;
                string value;

                int ind = 1;
                for (i = 0; i < index; i++)
                {
                    if (ds.Tables["ChildTable"].Columns[i].ColumnName != parentId)
                    {
                        value = "@value" + (ind).ToString();
                        da.UpdateCommand.Parameters.Add(value, ds.Tables["ChildTable"].Columns[i].DataType).Value = textBoxList[ind-1].Text;

                        ++ind;
                    }
                }

                value = "@value" + (ind).ToString();
                da.UpdateCommand.Parameters.Add(value, SqlDbType.VarChar).Value = id;
                
                da.UpdateCommand.ExecuteNonQuery();

                ds.Tables["ChildTable"].Clear();
                string selectcmd = ConfigurationManager.AppSettings["ChildSelectCmd"];
                da.SelectCommand = new SqlCommand(selectcmd, conexiune);
                da.Fill(ds, "ChildTable");

                conexiune.Close();
            }
        }

        private void deleteClick(object sender, EventArgs e)
        {
            if (childDataGridView.SelectedCells.Count > 0)
            {
                conexiune.Open();
                int selectedrowindex = childDataGridView.SelectedCells[0].RowIndex;

                DataGridViewRow selectedRow = childDataGridView.Rows[selectedrowindex];

                string toDeletedId = Convert.ToString(selectedRow.Cells[0].Value);

                string deleteCmd = ConfigurationManager.AppSettings["ChildDeleteCmd"];
                da.DeleteCommand = new SqlCommand(deleteCmd, conexiune);

                da.DeleteCommand.Parameters.Add("value", SqlDbType.VarChar).Value = toDeletedId;

                da.DeleteCommand.ExecuteNonQuery();
                ds.Tables["ChildTable"].Clear();
                string selectcmd = ConfigurationManager.AppSettings["ChildSelectCmd"];
                da.SelectCommand = new SqlCommand(selectcmd, conexiune);
                da.Fill(ds, "ChildTable");

                conexiune.Close();
            }
            else
                MessageBox.Show("Selectati o linie");
        }

    }
}
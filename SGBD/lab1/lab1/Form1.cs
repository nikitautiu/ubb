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

namespace lab1
{
    public partial class Form1 : Form
    {
        SqlConnection cs = new SqlConnection("Data Source=DESKTOP-MR3II8A\\SQLEXPRESS;Initial Catalog=Chinook;Integrated Security=True");
        SqlDataAdapter artistDa = new SqlDataAdapter();
        DataSet ArtistDs = new DataSet();
        BindingSource ArtistBs = new BindingSource();

        SqlDataAdapter AlbumDa = new SqlDataAdapter();
        DataSet AlbumDs = new DataSet();
        BindingSource AlbumBs = new BindingSource();

        public Form1()
        {
            InitializeComponent();


        }

        private void button1_Click(object sender, EventArgs e)
        {
            artistDa.SelectCommand = new SqlCommand("SELECT * FROM Artist", cs);
            ArtistDs.Clear();
            artistDa.Fill(ArtistDs);
            dataGridView1.DataSource = ArtistDs.Tables[0];
            ArtistBs.DataSource = ArtistDs.Tables[0];
        }

        private void button2_Click(object sender, EventArgs e)
        {
            var cmd = new SqlCommand("INSERT INTO Album VALUES (@AlbumId, @Title, @ArtistId)", cs);

            try
            {

                var rowInd = dataGridView1.SelectedCells[0].RowIndex;
                var ArtistId = dataGridView1["ArtistId", rowInd].Value;
                cmd.Parameters.AddWithValue("@AlbumId", textBox1.Text);
                cmd.Parameters.AddWithValue("@Title", textBox2.Text);
                cmd.Parameters.AddWithValue("@Artistid", ArtistId);


                cmd.Connection.Open();
                cmd.ExecuteNonQuery();

                AlbumDs.Clear();
                AlbumDa.Fill(AlbumDs);
                dataGridView2.DataSource = AlbumDs.Tables[0];
                AlbumBs.DataSource = AlbumDs.Tables[0];
                MessageBox.Show("Rand inserat cu succes!");
            }
            catch (Exception exc)
            {
                MessageBox.Show("A avut loc urmatoarea eroare:" + exc.ToString());
            }
            finally
            {
                cmd.Connection.Close();
            }
        }

        private void button4_Click(object sender, EventArgs e)
        {
            var cmd = new SqlCommand("UPDATE Album SET Title=@Title WHERE AlbumId=@AlbumId", cs);

            try
            {

                var rowInd = dataGridView2.SelectedCells[0].RowIndex;
                var AlbumId = dataGridView2["AlbumId", rowInd].Value;
                cmd.Parameters.AddWithValue("@AlbumId", AlbumId);
                cmd.Parameters.AddWithValue("@Title", textBox2.Text);

                cmd.Connection.Open();
                cmd.ExecuteNonQuery();

                AlbumDs.Clear();
                AlbumDa.Fill(AlbumDs);
                dataGridView2.DataSource = AlbumDs.Tables[0];
                AlbumBs.DataSource = AlbumDs.Tables[0];
                MessageBox.Show("Rand actualizat cu succes!");
            }
            catch (Exception exc)
            {
                MessageBox.Show("A avut loc urmatoarea eroare:" + exc.ToString());
            }
            finally
            {
                cmd.Connection.Close();
            }
        }

        private void dataGridView1_SelectionChanged(object sender, EventArgs e)
        {
            if (dataGridView1.SelectedCells.Count != 0)
            {
                AlbumDa.SelectCommand = new SqlCommand("SELECT * FROM Album WHERE ArtistId=@ArtistId", cs);
                var rowInd = dataGridView1.SelectedCells[0].RowIndex;
                var ArtistId = dataGridView1["ArtistId", rowInd].Value;
                AlbumDa.SelectCommand.Parameters.AddWithValue("@ArtistId", ArtistId);
                AlbumDs.Clear();
                AlbumDa.Fill(AlbumDs);
                dataGridView2.DataSource = AlbumDs.Tables[0];
                AlbumBs.DataSource = AlbumDs.Tables[0];
            }

        }

        private void button3_Click(object sender, EventArgs e)
        {
            // delete
            if (dataGridView2.SelectedCells.Count != 0)
            {
                var cmd = new SqlCommand("DELETE FROM Album WHERE AlbumId=@AlbumId", cs);

                try
                {

                    var rowInd = dataGridView2.SelectedCells[0].RowIndex;
                    var AlbumId = dataGridView2["AlbumId", rowInd].Value;
                    cmd.Parameters.AddWithValue("@AlbumId", AlbumId);
                    cmd.Connection.Open();
                    cmd.ExecuteNonQuery();

                    AlbumDs.Clear();
                    AlbumDa.Fill(AlbumDs);
                    dataGridView2.DataSource = AlbumDs.Tables[0];
                    AlbumBs.DataSource = AlbumDs.Tables[0];
                    MessageBox.Show("Rand sters cu succes!");
                }
                catch (Exception exc)
                {
                    MessageBox.Show("A avut loc urmatoarea eroare:" + exc.ToString());
                }
                finally
                {
                    cmd.Connection.Close();
                }
            }
        }

        private void label1_Click(object sender, EventArgs e)
        {

        }
    }
}

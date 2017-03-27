using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Festival
{
    public partial class LoginForm : Form
    {
        private Controller.Controller ctrl;
        private bool isLoggedin;

        public bool LoggedIn { get { return isLoggedin; } }

        public LoginForm(Controller.Controller ctrl)
        {
            this.ctrl = ctrl;
            isLoggedin = false;
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }

        private void label1_Click(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
            var name = NameText.Text;
            var password = PassText.Text;

            if (ctrl.Login(name, password))
            {
                isLoggedin = true;
                this.Close();
            }
            else 
                MessageBox.Show("Invalid user/password", "Error");
        }
    }
}

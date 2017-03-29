using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;
using Festival.Repository;

namespace Festival
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            var showRepo = new ShowDataSqlRepo("festival.db");
            var purchaseRepo = new PurchaseSqlRepo("festival.db");
            var userRepo = new UserSqlRepo("festival.db");
            var service = new Service.Service(showRepo, purchaseRepo, userRepo);
            var ctrl = new Controller.Controller(service);
            var loginForm = new LoginForm(ctrl);
            Application.Run(loginForm);

            if(loginForm.LoggedIn)
                Application.Run(new MainWindow(ctrl));
        }
    }
}

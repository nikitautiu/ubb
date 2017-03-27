using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity.Core.Common.CommandTrees;
using System.Linq;
using System.Text;
using System.Data.SQLite; 
using System.Threading.Tasks;

namespace Festival.Repository
{
    class DbUtils
    {

        private static Dictionary<string, IDbConnection> instances;

        private static IDbConnection createConnection(string filename)
        {
            // Windows Sqlite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
            String connectionString = "Data Source=" + filename + ";Version=3";
            return new SQLiteConnection(connectionString);
        }

        private static IDbConnection instance = null;

        public static IDbConnection getConnection(string filename)
        {
            if(instances == null)
                instances = new Dictionary<string, IDbConnection>();
            IDbConnection instance;
            try
            {
                instance = instances[filename];
            }
            catch (KeyNotFoundException e)
            {
                instance = null;
            }
            if (instance == null || instance.State == System.Data.ConnectionState.Closed)
            {
                instance = createConnection(filename);
                instance.Open();
                instances[filename] = instance;
            }
            return instance;
        }

   
        
    }
}

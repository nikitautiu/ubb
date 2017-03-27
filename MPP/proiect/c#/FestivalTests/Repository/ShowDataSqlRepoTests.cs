using Microsoft.VisualStudio.TestTools.UnitTesting;
using Festival.Repository;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Festival.Model;

namespace Festival.Repository.Tests
{
    [TestClass()]
    public class ShowDataSqlRepoTests
    {
        private ICrudRepository<int, ShowData> repo;
        [TestInitialize()]
        public void Init()
        {
            string databaseFile = "test.db";
            repo = new ShowDataSqlRepo(databaseFile);
        }

        [TestMethod()]
        public void ShowDataSqlRepoTest()
        {
            var entities = repo.findAll();
            Assert.AreEqual(entities.Count(), 6);
        }


    }
}
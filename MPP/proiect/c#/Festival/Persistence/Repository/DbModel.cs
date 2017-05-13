using System;
using System.Collections.Generic;

namespace Persistence.Repository
{
    using System;
    using System.Data.Entity;
    using System.Linq;

    public class DbModel : DbContext
    {
        // Your context has been configured to use a 'DbModel' connection string from your application's 
        // configuration file (App.config or Web.config). By default, this connection string targets the 
        // 'Persistence.Repository.DbModel' database on your LocalDb instance. 
        // 
        // If you wish to target a different database and/or database provider, modify the 'DbModel' 
        // connection string in the application configuration file.
        public DbModel()
            : base("name=DbModel")
        {
          
        }

        // Add a DbSet for each entity type that you want to include in your model. For more information 
        // on configuring and using a Code First model, see http://go.microsoft.com/fwlink/?LinkId=390109.

        // public virtual DbSet<MyEntity> MyEntities { get; set; }
        public virtual DbSet<Artist> Artists { get; set; }
        public virtual DbSet<Location> Locations { get; set; }
        public virtual DbSet<Show> Shows { get; set; }
        public virtual DbSet<User> Users { get; set; }
        public virtual DbSet<Purchase> Purchases { get; set; }


    }

    public class Artist
    {
        public int Id { get; set; }
        public string Name { get; set; }
    }

    public class Location
    {
        public int Id { get; set; }
        public string Name { get; set; }
    }

    public class Show
    {
        public int Id { get; set; }
        public int ArtistId { get; set; }
        public int LocationId { get; set; }
        public int AvailableSeats { get; set; }
        public DateTime StartTime { get; set; }


        public virtual Location Location { get; set; }
        public virtual Artist Artist { get; set; }
        public virtual List<Purchase> Purchases { get; set; }
    }

    public class User
    {
        public int Id { get; set; }
        public string Name { get; set; }
        public string PassHash { get; set; }
    }

    public class Purchase
    {
        public int Id { get; set; }
        public int ShowId { get; set; }
        public int Quanitity { get; set; }
        public string ClientName { get; set; }

        public virtual Show Show { get; set; }
    }
}
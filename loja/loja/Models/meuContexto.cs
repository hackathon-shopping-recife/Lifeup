using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Data.Entity;
using loja.Models;
namespace loja.Models
{
    public class meuContexto : DbContext
    {
        public meuContexto() : base(@"Server=DESKTOP-9K1K57S\SQLEXPRESS;Database=meuContexto;Trusted_Connection=True;")
        {
        }
        public System.Data.Entity.DbSet<loja.Models.Produto> produtos { get; set; }

        public System.Data.Entity.DbSet<loja.Models.Usuario> pessoa { get; set; }

        public System.Data.Entity.DbSet<loja.Models.Product> products { get; set; }
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace loja.Models
{
    public class Produto
    {
        public int id { get; set; }
        public int idLoja { get; set; }
        public double preco { get; set; }
        public double percentual { get; set; }
        public string nome { get; set; }
    }
}
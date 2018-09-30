using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace loja.Models
{
    public class Product
    {
        public int id { get; set; }
        public string title { get; set; }
        public string description { get; set; }
        public string category { get; set; }
        public string logo { get; set; }
        public double price { get; set; }

    }
}
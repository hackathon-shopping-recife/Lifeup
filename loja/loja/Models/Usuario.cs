using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace loja.Models
{
    public class Usuario
    {
        public int id { get; set; }
        public string login { get; set; }
        public string senha { get; set; }
    }
}
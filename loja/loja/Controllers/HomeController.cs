using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Web;
using System.Web.Mvc;
using Kendo.Mvc.Extensions;
using Kendo.Mvc.UI;
using loja.Models;
namespace loja.Controllers
{
    public class HomeController : Controller
    {
        // GET: Home

        private meuContexto db = new meuContexto();
        public ActionResult index()
        {         
            return View();
        }

        private List<Produto> pegarProdutos()
        {

            return db.produtos.ToList();
        }

        private void addProduct(Product pro)
        {
           
            
        }
        private void addProduto(Produto prod)
        {
            db.produtos.Add(prod);
            db.SaveChanges();
        }

        private string post(string serv , string dados)
        {
            HttpWebRequest http = WebRequest.Create(serv) as HttpWebRequest;
            http.Method = "POST";
            http.ContentType = "application/x-www-form-urlencoded";
            http.UserAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.0.1) Gecko/2008070208 Firefox/3.0.1";
            StreamWriter requestWriter = new StreamWriter(http.GetRequestStream());
            requestWriter.Write(dados);
            requestWriter.Close();
            StreamReader srResposta = new StreamReader(http.GetResponse().GetResponseStream());
            string resposta = srResposta.ReadToEnd();
            srResposta.Close();
            http.GetResponse().Close();
            return resposta;
        }


        public ActionResult Loja(Usuario u)
        {

         
            //db.pessoa.Add( new Pessoa(){ login = "jefferson", senha = "123" ,id = 5});
            //db.SaveChanges();
            //if( db.logar(u.login , u.senha))
            // {
            Loja l = new Loja();
                l.nome = "teste Adidas";
                return View(l);
            //}
            //return RedirectToAction("Home","index");
        }

        public ActionResult DashBoard()
        {
            return View();
        }
        [HttpPost]
        public ActionResult pregarProdutos([DataSourceRequest]DataSourceRequest request, int idLoja)
        {
           
            return Json( pegarProdutos().ToDataSourceResult(request));

        }
    
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult EditingCustom_Create([DataSourceRequest] DataSourceRequest request,
                 [Bind(Prefix = "models")]IEnumerable<Produto> products)
        {



            foreach (Produto item in products.ToList<Produto>())
            {
               addProduto(item);
            }
          
            return Json(pegarProdutos().ToDataSourceResult(request));
        }
        [AcceptVerbs(HttpVerbs.Post)]
        public ActionResult EditingCustom_Update([DataSourceRequest] DataSourceRequest request,
            [Bind(Prefix = "models")]IEnumerable<Produto> products)
        {
          
            return Json(pegarProdutos().ToDataSourceResult(request));
        }

        public ActionResult categoria()
        {
            return View();
        }


        [HttpPost]
        public ActionResult pegarCategorias([DataSourceRequest]DataSourceRequest request)
        {
            string serv = "http://10.98.4.83:3000/v1/product";
            string dados = "";
            List<categories> lista = new System.Web.Script.Serialization.JavaScriptSerializer().Deserialize<List<categories>>(post(serv, dados));
            return Json(lista.ToDataSourceResult(request));
        }

    }
}
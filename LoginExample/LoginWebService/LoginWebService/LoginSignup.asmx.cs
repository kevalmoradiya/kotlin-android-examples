using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Script.Services;
using System.Web.Services;

namespace LoginWebService
{
    /// <summary>
    /// Summary description for LoginSignup
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class LoginSignup : System.Web.Services.WebService
    {

        SqlConnection con;
        SqlDataAdapter da;
        SqlCommand cmd;
        DataSet ds = new DataSet();
        public LoginSignup()
        {
            con = new SqlConnection(ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString);
            da = new SqlDataAdapter();
            cmd = new SqlCommand();
            cmd.Connection = con;
        }

        [WebMethod]
        public ArrayList Login(string passstring)
        {
            ArrayList list = new ArrayList();
            ds.Clear();
            try
            {
                string[] user_string = passstring.Split(new string[] { "@@seprate@@" }, StringSplitOptions.None);
            
                con.Open();
                cmd.CommandText = "select * from [dbo].[users] where email = '" + user_string[0] + "' and password = '" + Crypto.Hash(user_string[1]) + "'";
                cmd.ExecuteNonQuery();
                da.SelectCommand = cmd;
                da.Fill(ds);

                if (ds.Tables[0].Rows.Count > 0)
                {
                    list.Add("true");

                }
                else
                {
                    list.Add("false");
                }
                con.Close();
            }
            catch(Exception e)
            {
                list.Add("false@@seprate@@" + e.ToString());
            }
            
            return list;
        }
        [WebMethod]
        public ArrayList Signup(string passstring)
        {
            ArrayList list = new ArrayList();
            try
            {
            string[] user_string = passstring.Split(new string[] { "@@seprate@@" }, StringSplitOptions.None);
            if (EmailExist(user_string[0]))
            {
                list.Add("false");
                return list;
            }
            ds.Clear();
            con.Open();
            //[0]Email,[1]Password
            
                cmd.CommandText = "insert into [dbo].[users] values('" + user_string[0] + "','" + Crypto.Hash(user_string[1]) + "')";
                cmd.ExecuteNonQuery();
                con.Close();
                list.Add("true");
            }
            catch(Exception e)
            {
                list.Add("false@@seprate@@"+e.ToString());
            }
           
            

            return list;
        }

        private Boolean EmailExist(string email)
        {

            ds.Clear();
            con.Open();
            cmd.CommandText = "select * from [dbo].[users] where email = '" + email + "'";
            cmd.ExecuteNonQuery();
            da.SelectCommand = cmd;
            da.Fill(ds);
            con.Close();
            if (ds.Tables[0].Rows.Count > 0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}

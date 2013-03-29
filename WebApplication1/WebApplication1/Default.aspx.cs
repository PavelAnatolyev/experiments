using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Microsoft.Office.Interop.Excel;

namespace WebApplication1
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            if (System.Threading.Thread.CurrentThread.CurrentCulture.LCID != 1033)
                System.Threading.Thread.CurrentThread.CurrentCulture = new CultureInfo(1033);


            Application excelApp = new Application();
            Workbook workbook = excelApp.Workbooks.Open("http://localhost/WebApplication1/ubu.xlsx", XlUpdateLinks.xlUpdateLinksNever, false, Type.Missing, Type.Missing, Type.Missing, true, Type.Missing, Type.Missing, Type.Missing, Type.Missing, Type.Missing, false, Type.Missing, XlCorruptLoad.xlNormalLoad);

            Worksheet worksheet = (Worksheet)workbook.Sheets.get_Item(1);
            Label1.Text = worksheet.Name;
            workbook.Close();
            excelApp = null;
        }
    }
}

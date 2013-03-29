using System;
using System.Collections.Generic;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Microsoft.Office.Interop.Excel;
using System.Globalization;

namespace WebApplication2
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void Button1_Click(object sender, EventArgs e)
        {
            Workbook workbook = App.Workbooks.Open("\\\\W7MACDEV-M\\temp\\ubu.xlsx");


            Label1.Text = "File is open";

            foreach (Worksheet worksheet in workbook.Sheets)
            {
                if (worksheet.Visible == XlSheetVisibility.xlSheetVisible)
                {
                    worksheet.Visible = XlSheetVisibility.xlSheetVeryHidden;
                }
                else
                {
                    worksheet.Visible = XlSheetVisibility.xlSheetVisible;
                }


            }
            workbook.Save();
            workbook.Close();
        }

        static Application s_App = null;
        static Application App
        {
            get
            {
                if (System.Threading.Thread.CurrentThread.CurrentCulture.LCID != 1033)
                    System.Threading.Thread.CurrentThread.CurrentCulture = new CultureInfo(1033);
                if (s_App == null)
                {
                    s_App = new Application();
                    s_App.UserControl = false;
                    s_App.DisplayAlerts = false;
                    s_App.EnableLargeOperationAlert = false;
                }

                return s_App;
            }
        }
    }

}
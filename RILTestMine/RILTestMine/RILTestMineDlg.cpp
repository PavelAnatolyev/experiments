// RILTestMineDlg.cpp : implementation file
//

#include "stdafx.h"
#include "RILTestMine.h"
#include "RILTestMineDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


void CALLBACK ResultCallback(DWORD dwCode,HRESULT hrCmdID,const void *lpData,DWORD cbData,DWORD dwParam)
{
	CRILTestMineDlg*	pClass	= (CRILTestMineDlg*)dwParam;
	CString			strData;

	strData.Format(TEXT("ResultCallback: Code: %X\r\n"),dwCode);

	if(!pClass)
		return;

	CString			strInfo(TEXT(""));

	if(pClass->GetCellInfoResult() == hrCmdID)
	{
		RILCELLTOWERINFO*	pCellInfo	= (RILCELLTOWERINFO*)lpData;

		strData.Format(TEXT("CellInfo:"));
		strInfo.Format(TEXT("\tMobile Country Code: %ld\r\n\tMobile Network Code: %d\r\n\t")
					   TEXT("Location Area Code: %ld\r\n\tCell ID: %ld\r\n\tBase Station ID: %ld\r\n\t")
					   TEXT("GPRS Cell ID: %ld\r\n\tGPRS Base Station ID: %ld"),

					   pCellInfo->dwMobileCountryCode,pCellInfo->dwMobileNetworkCode,
					   pCellInfo->dwLocationAreaCode,pCellInfo->dwCellID,pCellInfo->dwBaseStationID,
					   pCellInfo->dwGPRSCellID,pCellInfo->dwGPRSBaseStationID);
	}

	CString			strMessage	= strData;

	strMessage	+= TEXT("\r\n");
	strMessage	+= strInfo;

	if(pClass)
		pClass->AddLog(strMessage);
}

void CALLBACK NotifyCallback(DWORD dwCode,const void *lpData,DWORD cbData, DWORD dwParam)
{
	CRILTestMineDlg*	pClass	= (CRILTestMineDlg*)dwParam;
	DWORD			dwClass	= (dwCode & RIL_NCLASS_ALL);
	CString			strData;
	CString			strParam(TEXT(""));

	strData.Format(TEXT("NotifyCallback: Code: %X\r\n"),dwClass);

	switch(dwClass)
	{
		case RIL_NCLASS_FUNCRESULT:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_FUNCRESULT"));	break;
		case RIL_NCLASS_CALLCTRL:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_CALLCTRL"));		break;
		case RIL_NCLASS_MESSAGE:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_MESSAGE"));		break;
		case RIL_NCLASS_NETWORK:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_NETWORK"));		break;
		case RIL_NCLASS_SUPSERVICE:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_SUPSERVICE"));	break;
		case RIL_NCLASS_PHONEBOOK:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_PHONEBOOK"));	break;
		case RIL_NCLASS_SIMTOOLKIT:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_SIMTOOLKIT"));	break;
		case RIL_NCLASS_MISC:			strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_MISC"));			break;
		case RIL_NCLASS_RADIOSTATE:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_RADIOSTATE"));	break;
		case RIL_NCLASS_POLLING:		strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_POLLING"));		break;
		case RIL_NCLASS_NDIS:			strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_NDIS"));			break;
		case RIL_NCLASS_DEVSPECIFIC:	strData.Format(TEXT("%s"),TEXT("RIL_NCLASS_DEVSPECIFIC"));	break;
	}

	switch(dwCode)
	{
		case RIL_NOTIFY_RADIOEQUIPMENTSTATECHANGED:
		{
			RILEQUIPMENTSTATE*	pState	= (RILEQUIPMENTSTATE*)lpData;

			strParam.Format(TEXT("\tRadio Support: %X\r\n\tEquipment State: %X\r\n\tReady State: %X"),
						   pState->dwRadioSupport,pState->dwEqState,pState->dwReadyState);

			break;
		}
		case RIL_NOTIFY_RADIOPRESENCECHANGED:
		{
			DWORD	dwPresence	= (DWORD)*((DWORD*)lpData);

			switch(dwPresence)
			{
				case RIL_RADIOPRESENCE_NOTPRESENT:	strParam.Format(TEXT("\tRadio module is not present"));		break;
				case RIL_RADIOPRESENCE_PRESENT:		strParam.Format(TEXT("\tRadio module is present"));			break;
				default:							strParam.Format(TEXT("\tUnknown value: %X"),dwPresence);	break;
			}

			break;
		}
		case RIL_NOTIFY_MESSAGE:
		{
			RILMESSAGE*	pMessage	= (RILMESSAGE*)lpData;

			switch(pMessage->dwType)
			{
				case RIL_MSGTYPE_IN_DELIVER:
				{
					strParam.Format(TEXT("\tMessage From: %s\r\n\tTime: %02d-%02d-%02d %02d:%02d:%02d\r\n\tCode: %X"),
									pMessage->msgInDeliver.raOrigAddress.wszAddress,
									pMessage->msgInDeliver.stSCReceiveTime.wMonth,
									pMessage->msgInDeliver.stSCReceiveTime.wDay,
									pMessage->msgInDeliver.stSCReceiveTime.wYear,
									pMessage->msgInDeliver.stSCReceiveTime.wHour,
									pMessage->msgInDeliver.stSCReceiveTime.wMinute,
									pMessage->msgInDeliver.stSCReceiveTime.wSecond,
									dwCode);

					break;
				}
			}
		}
	}

	CString		strMessage	= strData;
	
	if(strParam.GetLength() > 0)
	{
		strMessage	+= TEXT("\r\n");
		strMessage	+= strParam;
	}

	if(pClass)
		pClass->AddLog(strMessage);
}

// CRILTestMineDlg dialog

CRILTestMineDlg::CRILTestMineDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CRILTestMineDlg::IDD, pParent)
{
	m_hIcon 				= AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	
}

void CRILTestMineDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_DATA, m_wndData);
}

BEGIN_MESSAGE_MAP(CRILTestMineDlg, CDialog)
	ON_WM_SIZE()
	//}}AFX_MSG_MAP
	ON_WM_DESTROY()
	ON_COMMAND(ID_MENU_ABOUT, &CRILTestMineDlg::OnMenuAbout)
END_MESSAGE_MAP()

// CRILTestMineDlg message handlers

void CRILTestMineDlg::AddLog(LPCTSTR lpszLog)
{
	CString		strText;

	m_wndData.GetWindowText(strText);

	if(strText.GetLength() > 0)
		strText	+=	TEXT("\r\n");

	strText	+= lpszLog;

	m_wndData.SetWindowText(strText);
}


BOOL CRILTestMineDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// Set the icon for this dialog.  The framework does this automatically
	//  when the application's main window is not a dialog
	SetIcon(m_hIcon, TRUE);			// Set big icon
	SetIcon(m_hIcon, FALSE);		// Set small icon

	SHMENUBARINFO info; 
	
	info.cbSize			= sizeof(info); 
	info.hwndParent		= m_hWnd; 
	info.dwFlags		= SHCMBF_HMENU | SHCMBF_HIDESIPBUTTON; 
	info.nToolBarId		= IDR_MENU; 
	info.hInstRes		= ::AfxGetInstanceHandle(); 
	info.nBmpId			= 0; 
	info.cBmpImages		= 0; 
	
	SHCreateMenuBar(&info);

	HRESULT	hr = RIL_Initialize(1,ResultCallback,NotifyCallback,
								/*RIL_NCLASS_ALL*/0x00ff0000,(DWORD)this,&m_hRIL);

	if(FAILED(hr))
	{
		m_hRIL	= NULL;
    CString string;
    _stprintf(string.GetBuffer(1024),_T("Failed to initialize RIL: 0x%08x"), hr);
    string.ReleaseBuffer();
		AfxMessageBox(string);
	}
	else
	{
		m_hCellInfoResult		= RIL_GetCellTowerInfo(m_hRIL);
	}
	
	return TRUE;  // return TRUE  unless you set the focus to a control
}

void CRILTestMineDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType,cx,cy);

	if(m_wndData.GetSafeHwnd())
	{
		m_wndData.SetWindowPos(NULL,DRA::SCALEX(X_OFFSET),DRA::SCALEX(Y_OFFSET),
							   DRA::SCALEX(cx - X_OFFSET * 2),DRA::SCALEX(cy - X_OFFSET * 2),
							   SWP_NOZORDER);
	}
}

void CRILTestMineDlg::OnDestroy()
{
	if(m_hRIL)
		RIL_Deinitialize(m_hRIL);

	CDialog::OnDestroy();
}

void CRILTestMineDlg::OnMenuAbout()
{
	AfxMessageBox(TEXT("RIL Sample\r\nKrishnaraj Varma\r\nhttp://www.krvarma.com\r\nvarma@krvarma.com"));
}

// CRILTestMineDlg dialog

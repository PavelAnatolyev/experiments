// RILTestMineDlg.h : header file
//

#pragma once

#define X_OFFSET		10
#define Y_OFFSET		10

// CRILTestMineDlg dialog
class CRILTestMineDlg : public CDialog
{
// Construction
public:
	CRILTestMineDlg(CWnd* pParent = NULL);	// standard constructor

// Dialog Data
	enum { IDD = IDD_RILTESTMINE_DIALOG };

protected:  // control bar embedded members
	CCommandBar m_dlgCommandBar;

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV support
public:
	void AddLog(LPCTSTR lpszLog);

public:
	HRESULT GetCellInfoResult() const
	{
		return m_hCellInfoResult;
	}

private:
	HRIL		m_hRIL;
	HRESULT		m_hCellInfoResult;

// Implementation
protected:
	HICON m_hIcon;

	// Generated message map functions
	virtual BOOL OnInitDialog();
	afx_msg void OnSize(UINT /*nType*/, int /*cx*/, int /*cy*/);

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnDestroy();
	CEdit m_wndData;
	afx_msg void OnMenuAbout();

};

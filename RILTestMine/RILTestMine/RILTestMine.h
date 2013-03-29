// RILTestMine.h : main header file for the PROJECT_NAME application
//

#pragma once

#ifndef __AFXWIN_H__
	#error "include 'stdafx.h' before including this file for PCH"
#endif

#ifdef SMARTPHONE2003_UI_MODEL
#include "resourcesp.h"
#endif

// CRILTestMineApp:
// See RILTestMine.cpp for the implementation of this class
//

class CRILTestMineApp : public CWinApp
{
public:
	CRILTestMineApp();
	
// Overrides
public:
	virtual BOOL InitInstance();

// Implementation

	DECLARE_MESSAGE_MAP()
};

extern CRILTestMineApp theApp;

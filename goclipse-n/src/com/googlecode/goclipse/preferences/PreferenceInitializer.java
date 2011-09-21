package com.googlecode.goclipse.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.SWT;

import com.googlecode.goclipse.Activator;
import com.googlecode.goclipse.editors.IColorConstants;


/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	private static final String OS_ARCH = "os.arch";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		//SysUtils.debug("Loading Go Plug-in Preferences...");
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_BOOLEAN, true);
		store.setDefault(PreferenceConstants.FIELD_USE_HIGHLIGHTING, PreferenceConstants.VALUE_HIGHLIGHTING_TRUE);
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_KEYWORD_COLOR,          IColorConstants.KEYWORD         );
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_VALUE_COLOR,            IColorConstants.VALUE           );
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_PRIMITIVE_COLOR,        IColorConstants.PRIMITIVE       );
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_COMMENT_COLOR,          IColorConstants.COMMENT         ); 
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_BUILTIN_FUNCTION_COLOR, IColorConstants.BUILTIN_FUNCTION);
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_STRING_COLOR,           IColorConstants.STRING          );
		PreferenceConverter.setDefault(store, PreferenceConstants.FIELD_SYNTAX_MULTILINE_STRING_COLOR, IColorConstants.MULTILINE_STRING);  
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_KEYWORD_STYLE,          SWT.BOLD           );
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_VALUE_STYLE,            SWT.BOLD|SWT.ITALIC);
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_PRIMITIVE_STYLE,        SWT.ITALIC         );
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_COMMENT_STYLE,          SWT.NORMAL         );
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_BUILTIN_FUNCTION_STYLE, SWT.BOLD           );
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_STRING_STYLE,           SWT.NORMAL         );
		store.setDefault(PreferenceConstants.FIELD_SYNTAX_MULTILINE_STRING_STYLE, SWT.NORMAL         );
		store.setDefault(PreferenceConstants.P_STRING,	"Default value");	

		if (Util.isWindows()){
			store.setDefault(PreferenceConstants.GOOS, PreferenceConstants.OS_WINDOWS);
		} else if (Util.isLinux()) {
			store.setDefault(PreferenceConstants.GOOS, PreferenceConstants.OS_LINUX);
		} else if (Util.isMac()) {
			store.setDefault(PreferenceConstants.GOOS, PreferenceConstants.OS_DARWIN);
		}

		if (isAMD64()){
			store.setDefault(PreferenceConstants.GOARCH, PreferenceConstants.ARCH_AMD64);
		} else if (is386()){
			store.setDefault(PreferenceConstants.GOARCH, PreferenceConstants.ARCH_386);
		}
	}
	
	private static boolean isAMD64() {
		String osProp = System.getProperty(OS_ARCH);
		return "x86_64".equals(osProp) || "amd64".equals(osProp);
	}

	private static boolean is386() {
		String osProp = System.getProperty(OS_ARCH);
		return "i386".equals(osProp) || "x86".equals(osProp) || "i686".equals(osProp);
	}
	
	public static String getDefaultPlatformChar() {
		if (isAMD64()){
			return "6";
		} else if (is386()) {
			return "8";
		}
		return null;
	}
	
	public static String getDefaultCompilerName() {
		String platformChar = getDefaultPlatformChar();
		if (platformChar == null){
			return null;
		}
		return platformChar+"g"+(Util.isWindows()?".exe":"");
	}
	
	public static String getDefaultLinkerName() {
		String platformChar = getDefaultPlatformChar();
		if (platformChar == null){
			return null;
		}
		return platformChar+"l"+(Util.isWindows()?".exe":"");
	}
	
	public static String getDefaultPackerName() {
		return "gopack"+(Util.isWindows()?".exe":"");
	}

	public static String getDefaultGotestName() {
		return "gotest" +(Util.isWindows()?".exe":"");
	}

	public static String getDefaultGofmtName() {
		return "gofmt" +(Util.isWindows()?".exe":"");
	}

	
}

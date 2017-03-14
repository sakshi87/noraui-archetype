#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.utils;

import java.util.Properties;

import org.apache.log4j.Logger;

import noraui.application.Application;
import noraui.gherkin.GherkinFactory;
import noraui.utils.Context;

public class ${robotName}Context extends Context {

    /**
     * Specific logger.
     */
    private static final Logger logger = Logger.getLogger(${robotName}Context.class);

    public static final String ${targetApplication.toUpperCase()}_KEY = "${targetApplication}";
    public static final String ${targetApplication.toUpperCase()}_HOME = "${targetApplication.toUpperCase()}_HOME";
    public static final String ${targetApplication.toUpperCase()}_APP = "${targetApplication}.app";

    public static final String GO_TO_${targetApplication.toUpperCase()}_HOME = "GO_TO_${targetApplication.toUpperCase()}_HOME";
    public static final String CLOSE_WINDOW_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME = "CLOSE_WINDOW_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME";
    public static final String CLOSE_ALL_WINDOWS_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME = "CLOSE_ALL_WINDOWS_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME";

    private String ${targetApplication}Home; // ${targetApplication.toUpperCase()} home url

    /**
     * Constructor is useless because all attributes are static
     */
    private ${robotName}Context() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void initializeEnv(ClassLoader loader, String propertiesFile) {
        super.initializeEnv(loader, propertiesFile);
        logger.info("initializeEnv in ${robotName}Context");
        Properties applicationProperties = initPropertyFile(propertiesFile, loader);

        // Paths configuration
        getDataInputProvider().setDataInPath(System.getProperty("resourcespath") + "/data/in/");
        getDataOutputProvider().setDataOutPath(System.getProperty("resourcespath") + "/data/out/");
        GherkinFactory.setResourcesPath(System.getProperty("resourcespath"));

        // Urls configuration
        ${targetApplication}Home = setProperty(${targetApplication.toUpperCase()}_KEY, applicationProperties);

        // Selectors configuration
        initApplicationDom(selectorsVersion, ${targetApplication.toUpperCase()}_KEY);

        norauiExceptionCallbacks.put(GO_TO_${targetApplication.toUpperCase()}_HOME, STEPS_COMMON_STEPS_CLASS_QUALIFIED_NAME, GO_TO_URL_METHOD_NAME, ${targetApplication.toUpperCase()}_HOME);
        norauiExceptionCallbacks.put(CLOSE_WINDOW_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME, STEPS_COMMON_STEPS_CLASS_QUALIFIED_NAME, "closeWindowAndSwitchTo", ${targetApplication.toUpperCase()}_KEY, ${targetApplication.toUpperCase()}_HOME);
        norauiExceptionCallbacks.put(CLOSE_ALL_WINDOWS_AND_SWITCH_TO_${targetApplication.toUpperCase()}_HOME, STEPS_COMMON_STEPS_CLASS_QUALIFIED_NAME, "closeAllWindowsAndSwitchTo", ${targetApplication.toUpperCase()}_KEY);

        applications.put(${targetApplication.toUpperCase()}_KEY, new Application(${targetApplication.toUpperCase()}_HOME, ${targetApplication}Home));

    }

    /**
     * Get context singleton.
     *
     * @return context instance
     */
    public static Context getInstance() {
        if (instance == null || !(instance instanceof ${robotName}Context)) {
            instance = new ${robotName}Context();
        }
        return instance;
    }

    public String get${targetApplication.substring(0,1).toUpperCase()}${targetApplication.substring(1)}Home() {
        return ${targetApplication}Home;
    }

}
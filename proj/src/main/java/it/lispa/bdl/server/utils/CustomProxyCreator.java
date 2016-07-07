package it.lispa.bdl.server.utils;

import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.SourceWriter;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;


/**
 * Class CustomProxyCreator.
 */
public class CustomProxyCreator extends ProxyCreator {

    /** La Costante methodStrTemplate. */
    private final static String methodStrTemplate = "@Override\n"
											            + "protected <T> com.google.gwt.http.client.Request doInvoke(ResponseReader responseReader, "
											            + "String methodName, RpcStatsContext statsContext, String requestData, "
											            + "com.google.gwt.user.client.rpc.AsyncCallback<T> callback) {\n"
											            + "${method-body}" + "}\n";

    /**
     * Istanzia un nuovo custom proxy creator.
     *
     * @param serviceIntf  service intf
     */
    public CustomProxyCreator(JClassType serviceIntf) {
        super(serviceIntf);
    }

    /* (non-Javadoc)
     * @see com.google.gwt.user.rebind.rpc.ProxyCreator#generateProxyMethods(com.google.gwt.user.rebind.SourceWriter, com.google.gwt.user.rebind.rpc.SerializableTypeOracle, com.google.gwt.core.ext.typeinfo.TypeOracle, java.util.Map)
     */
    protected void generateProxyMethods(SourceWriter w, SerializableTypeOracle serializableTypeOracle, TypeOracle typeOracle, Map<JMethod, JMethod> syncMethToAsyncMethMap) {
        
    	// generate standard proxy methods
        super.generateProxyMethods(w, serializableTypeOracle, typeOracle, syncMethToAsyncMethMap);

        // generate additional method
        overrideDoInvokeMethod(w);
    }

    /**
     * Override do invoke method.
     *
     * @param w  w
     */
    private void overrideDoInvokeMethod(SourceWriter w) {
        StringBuilder methodBody = new StringBuilder();
        methodBody.append("final com.google.gwt.user.client.rpc.AsyncCallback newAsyncCallback = new it.lispa.bdl.shared.utils.CustomAsyncCallback(callback);\n");
        methodBody.append("return super.doInvoke(responseReader, methodName, statsContext, requestData, newAsyncCallback);\n");

        String methodStr = methodStrTemplate.replace("${method-body}",methodBody);
        w.print(methodStr);
    }

}
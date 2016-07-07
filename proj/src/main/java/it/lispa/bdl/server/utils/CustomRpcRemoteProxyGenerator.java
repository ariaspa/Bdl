package it.lispa.bdl.server.utils;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

/**
 * Class CustomRpcRemoteProxyGenerator.
 */
public class CustomRpcRemoteProxyGenerator extends ServiceInterfaceProxyGenerator {

    /* (non-Javadoc)
     * @see com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator#createProxyCreator(com.google.gwt.core.ext.typeinfo.JClassType)
     */
    @Override
    protected ProxyCreator createProxyCreator(JClassType remoteService) {
        return new CustomProxyCreator(remoteService);
    }
}
/*
 * BlocksHub a library plugin providing easy access to block loggers 
 * and block access controllers.
 * Copyright (c) 2016, SBPrime <https://github.com/SBPrime/>
 * Copyright (c) BlocksHub contributors
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted free of charge provided that the following 
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution,
 * 3. Redistributions of source code, with or without modification, in any form 
 *    other then free of charge is not allowed,
 * 4. Redistributions in binary form in any form other then free of charge is 
 *    not allowed.
 * 5. Any derived work based on or containing parts of this software must reproduce 
 *    the above copyright notice, this list of conditions and the following 
 *    disclaimer in the documentation and/or other materials provided with the 
 *    derived work.
 * 6. The original author of the software is allowed to change the license 
 *    terms or the entire license of the software as he sees fit.
 * 7. The original author of the software is allowed to sublicense the software 
 *    or its parts using any license terms he sees fit.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.primesoft.blockshub.api.base;

import org.primesoft.blockshub.IBlocksHubApi;
import org.primesoft.blockshub.api.IBlocksHubEndpoint;
import org.primesoft.blockshub.api.ILog;
import org.primesoft.blockshub.api.IPlatform;
import org.primesoft.blockshub.utils.LoggerProvider;

/**
 *
 * @author SBPrime
 */
public abstract class BaseEndpoint implements IBlocksHubEndpoint, ILog {
    private final String m_endpointName;

    private final String m_pluginName;

    @Override
    public String getName() {
        return m_endpointName;
    }

    protected BaseEndpoint(String pluginName) {
        this(pluginName, pluginName);
    }

    protected BaseEndpoint(String endpointName, String pluginName) {
        m_endpointName = endpointName;
        m_pluginName = pluginName;
    }

    /**
     * Log a message
     * @param message 
     */
    @Override
    public void log(String message) {
        if (message == null) {
            return;
        }
        
        LoggerProvider.log(String.format("%1$s: %2$s", m_pluginName, message));
    }
    
    @Override
    public boolean initialize(IBlocksHubApi api, IPlatform platform) {
        if (api == null || platform == null) {
            return false;
        }

        Object plugin = platform.getPlugin(m_pluginName);
        if (plugin == null) {
            log("plugin not found.");
            return false;
        }

        return finalizeInitialization(api, platform, plugin);
    }

    /**
     * Finalize the endpoint initialization
     * @param api
     * @param platform
     * @param plugin
     * @return 
     */
    protected abstract boolean finalizeInitialization(IBlocksHubApi api, IPlatform platform, Object plugin);
}

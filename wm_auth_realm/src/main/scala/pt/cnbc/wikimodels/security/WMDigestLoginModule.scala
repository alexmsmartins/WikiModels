/*
 * WMDigestLoginModule.scala
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package pt.cnbc.wikimodels.security

//TODO - cleanup imports -> perheaps even in other files
import com.sun.enterprise.deployment.Group
import com.sun.enterprise.security.auth.realm.NoSuchRealmException
import com.sun.logging.LogDomains
import java.util.Enumeration
import java.util.Map
import java.util.logging.Logger
import javax.security.auth.Subject
import javax.security.auth.callback.CallbackHandler
import javax.security.auth.login.LoginException
import javax.security.auth.spi.LoginModule
import com.sun.enterprise.util.i18n.StringManager;
import java.util.Iterator
import java.util.Set
import java.util.logging.Level
import com.sun.web.security.PrincipalGroupFactory
import com.sun.enterprise.deployment.PrincipalImpl
import com.sun.enterprise.deployment.Group
import com.sun.enterprise.security.auth.digest.api.DigestAlgorithmParameter
import com.sun.enterprise.security.auth.digest.api.Password
import com.sun.enterprise.security.auth.login.DigestLoginModule
import com.sun.enterprise.security.auth.realm.DigestRealm
import com.sun.enterprise.security.auth.realm.Realm

import com.sun.enterprise.security.auth.realm.BadRealmException
import com.sun.enterprise.security.auth.realm.NoSuchUserException
import com.sun.enterprise.security.auth.realm.NoSuchRealmException
import com.sun.enterprise.security.auth.realm.AuthenticationHandler
import com.sun.enterprise.security.auth.realm.InvalidOperationException

import java.util.Enumeration
import java.util.logging.Logger


import pt.cnbc.wikimodels.utils.StringEnumeration


class WMDigestLoginModule[_] extends DigestLoginModule{
    override protected def getGroups(username:String):java.util.Enumeration[_] = {
        try {
            return this.getRealm().getGroupNames(username)
        } catch{
            case ex:InvalidOperationException => {
                Logger.getLogger("global").log(Level.SEVERE, null, ex)
            }
            case ex:NoSuchUserException => {
                Logger.getLogger("global").log(Level.SEVERE, null, ex)
            }
        }
        return null
    }
}
/*
 * SimpleAuthRealm.scala
 *
 */

package pt.cnbc.wikimodels.security

//import java.util._
import java.util.logging.Logger
import java.util.logging.Level
import com.sun.logging.LogDomains
import java.io._
import java.security._
import javax.security.auth.login._
import com.sun.appserv.security.AppservRealm._
import com.sun.enterprise.security.auth.realm.User
import com.sun.enterprise.security.auth.realm.Realm
import com.sun.enterprise.security.auth.realm.DigestRealmBase
import com.sun.enterprise.security.auth.realm.BadRealmException
import com.sun.enterprise.security.auth.realm.NoSuchUserException
import com.sun.enterprise.security.auth.realm.NoSuchRealmException
import com.sun.enterprise.security.auth.realm.AuthenticationHandler
import com.sun.enterprise.security.auth.realm.InvalidOperationException
import com.sun.enterprise.server._
import com.sun.enterprise.security.RealmConfig
import com.sun.enterprise.security.util._
import com.sun.enterprise.security.auth.realm.IASRealm
import sun.misc.BASE64Decoder
import sun.misc.BASE64Encoder


import com.sun.enterprise.security.auth.digest.impl.DigestProcessor
import com.sun.enterprise.security.auth.digest.api.Password
import com.sun.enterprise.security.auth.digest.impl.KeyDigestAlgoParamImpl
import com.sun.enterprise.security.auth.digest.api.DigestAlgorithmParameter
import com.sun.enterprise.security.auth.digest.api.Key
import com.sun.enterprise.security.auth.digest.api.NestedDigestAlgoParam
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.spec.AlgorithmParameterSpec
import java.util.logging.Level
import java.util.logging.Logger
import org.apache.catalina.util.MD5Encoder
import com.sun.enterprise.security.auth.digest.api.Constants._



import com.sun.enterprise.util.i18n.StringManager;

import pt.cnbc.wikimodels.utils.StringEnumeration


/**
 * Realm wrapper for supporting password authentication based on information
 * persisted on a triple store. It resluts on the emrge between
 * <code>com.sun.enterprise.security.auth.relam.DigestRealm </code>
 * and <code>com.sun.appserv.security.AppservRealm</code>. AppservRealm was used
 * only to fill in the gaps.
 *
 * Class realms that supports Digest based authentication by getting user
 * information from a Triple Store
 * @author Alexandre Martins
 */
class WMDigestRealm extends DigestRealmBase{
    /*def validate(passwd:Password, params:Array[DigestAlgorithmParameter] ):Boolean = {
     try {
     new DigestValidatorImpl().validate(passwd, params);
     } catch {
     case ex:NoSuchAlgorithmException => {
     WMDigestRealm._logger.log(Level.SEVERE,"invalid.digest.algo",ex)
     }
     throw ex
     }
     false
     }*/


    /**
     * the implementors validate function will have to retrieve the password
     * from the backend and invoke the validate method of the super class
     */
     override def validate(username:String,
                           params:Array[DigestAlgorithmParameter]):Boolean = {

                          
            val passwd = getPassword(username)
            validate(passwd, params)
        }

     private def getPassword(username:String):Password = {
            val s = "pass"
            val m = MessageDigest.getInstance("MD5")
            m.update(s.getBytes(),0,s.length())
            new Password() {
                override def getValue():Array[Byte] = {
                    m.digest()
                }

                override def getType():Int = {
                    return Password.HASHED
                }
            }
        }



     //keep this just in case AppservRealm goes bananas again
     //and gives a "AppserRealm class is broken"
     /*private case class DigestValidatorImpl extends DigestProcessor {

      private var realm:Realm = null;
      private var data:DigestAlgorithmParameter  = null;
      private var clientResponse:DigestAlgorithmParameter  = null;
      private var key:DigestAlgorithmParameter = null;
      private var algorithm:String  = "MD5";
      private var realmName:String = null;

      def validate(passwd:Password ,
      params:Array[DigestAlgorithmParameter]
      ):Boolean =
      {

      for (p <- params) {
      var dap:DigestAlgorithmParameter = p;
      if (A1.equals(dap.getName()) && (dap.isInstanceOf[Key])) {
      key = dap;
      } else if (RESPONSE.equals(dap.getName())) {
      clientResponse = dap;
      } else {
      data = dap;
      }
      }
      setPassword(passwd);

      try {
      var p1:Array[scala.Byte]  = valueOf(key)
      var p2:Array[scala.Byte]  = valueOf(data)
      var bos:ByteArrayOutputStream = new ByteArrayOutputStream
      bos.write(p1);
      bos.write(":".getBytes());
      bos.write(p2);

      var md:MessageDigest = MessageDigest.getInstance(algorithm)
      var derivedKey:Array[scala.Byte] = null
      var dk:Array[scala.Byte] = md.digest(bos.toByteArray)
      var tmp:String = new MD5Encoder().encode(dk);
      derivedKey = tmp.getBytes();
      var suppliedKey:Array[scala.Byte] = clientResponse.getValue
      var result:Boolean = true
      if (derivedKey.length == suppliedKey.length) {
      var i = 0
      while (i < derivedKey.length && result){
      if (!(derivedKey(i) == suppliedKey(i))) {
      result = false
      //TODO - OPTIMIZE to exit the for comprehension ASAP
      }
      }
      } else {
      result = false;
      }
      result
      } catch {
      case ex:IOException => {
      var msg:Array[String] = new Array[String](1)
      msg(0) = ex.getMessage()
      WMDigestRealm._logger.log(Level.SEVERE,"digest.error",msg)
      false
      }
            
      }
      }
      }*/

     /**
      * Returns a short (preferably less than fifteen characters) description
      * of the kind of authentication which is supported by this realm.
      *
      * @return description of the kind of authentication that is directly
      *	supported by this realm.
      */
     override def getAuthType:String = {
            WMDigestRealm.AUTH_TYPE
        }

     /**
      * Returns names of all the users in this particular realm.
      *
      * @return enumeration of user names (strings)
      * @exception BadRealmException if realm data structures are bad
      */
     override def getUserNames:java.util.Enumeration[String] = {
            import pt.cnbc.wikimodels.utils.StringEnumeration
            //TODO - getGroups method is a testing stub, implement it
            StringEnumeration(List("alex","goncalo"))
        }

     /**
      * Returns the information recorded about a particular named user.
      *
      * @param name name of the user whose information is desired
      * @return the user object
      * @exception NoSuchUserException if the user doesn't exist
      * @exception BadRealmException if realm data structures are bad
      */
     override def getUser(name:String):User = {
            throw new InvalidOperationException("")
        }

     /**
      * Returns names of all the groups in this particular realm.
      *
      * @return enumeration of group names (strings)
      * @exception BadRealmException if realm data structures are bad
      */
     override def getGroupNames:java.util.Enumeration[String] = {
            //TODO - getGroups method is a testing stub, implement it
            //TODO - replcated code with WMDigestLoginModule.getGroups
            StringEnumeration(List("user", "admin"))
        }

     /**
      * Returns the name of all the groups that this user belongs to
      * @param username name of the user in this realm whose group listing
      * is needed.
      * @return enumeration of group names (strings)
      * @exception InvalidOperationException thrown if the realm does not
      * support this operation - e.g. Certificate realm does not support this
      * operation
      */
     override def getGroupNames(username:String):java.util.Enumeration[String] = {
            //TODO - getGroups method is a testing stub, implement it
            //TODO - replcated code with WMDigestRealm.getGroups
            username match {
                case "alex" => StringEnumeration(List("user"))
                case "goncalo" => StringEnumeration(List("admin"))
                case "adriana" => StringEnumeration(List("admin", "user"))
                case "adriana2" => StringEnumeration(List("user","admin"))
                case _ => StringEnumeration(List())
            }
        }

     /**
      * Refreshes the realm data so that new users/groups are visible.
      *
      * @exception BadRealmException if realm data structures are bad
      */
     override def refresh:Unit = {
            throw new BadRealmException
        }
     }


     object WMDigestRealm{
            //TODO - DELETE UNNECESSARY CONSTANTS -> this was copy/pasted

            val JAAS_CONTEXT_PARAM="jaas-context";
            protected val _logger:Logger = LogDomains.getLogger(LogDomains.SECURITY_LOGGER);
            protected val sm = StringManager.getManager("com.sun.enterprise.security.auth.realm")


            // Descriptive string of the authentication type of this realm.
            val AUTH_TYPE = "jdbc";
            val PRE_HASHED = "HASHED";
            val PARAM_DATASOURCE_JNDI = "datasource-jndi";
            val PARAM_DB_USER = "db-user";
            val PARAM_DB_PASSWORD = "db-password";

            val PARAM_DIGEST_ALGORITHM = "digest-algorithm";
            val DEFAULT_DIGEST_ALGORITHM = "MD5";
            val NONE = "none";

            val PARAM_ENCODING = "encoding";
            val HEX = "hex";
            val BASE64 = "base64";
            val DEFAULT_ENCODING = HEX; // for digest only

            val PARAM_CHARSET = "charset";
            val PARAM_USER_TABLE = "user-table";
            val PARAM_USER_NAME_COLUMN = "user-name-column";
            val PARAM_PASSWORD_COLUMN = "password-column";
            val PARAM_GROUP_TABLE = "group-table"
            val PARAM_GROUP_NAME_COLUMN = "group-name-column"

            private val HEXADECIMAL = Array( '0', '1', '2', '3',
                                            '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' )
        }

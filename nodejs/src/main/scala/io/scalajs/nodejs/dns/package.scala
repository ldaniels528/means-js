package io.scalajs.nodejs

import io.scalajs.RawOptions
import io.scalajs.util.PromiseHelper._

import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.|

/**
  * dns package object
  * @author lawrence.daniels@gmail.com
  */
package object dns {

  type RRType = String

  // IPV4 addresses, default
  val RRTYPE_A: RRType = "A"

  // IPV6 addresses
  val RRTYPE_AAAA: RRType = "AAAA"

  // mail exchange records
  val RRTYPE_MX: RRType = "MX"

  // text records
  val RRTYPE_TXT: RRType = "TXT"

  // SRV records
  val RRTYPE_SRV: RRType = "SRV"

  // PTR records
  val RRTYPE_PTR: RRType = "PTR"

  // name server records
  val RRTYPE_NS: RRType = "NS"

  // canonical name records
  val RRTYPE_CNAME: RRType = "CNAME"

  // start of authority record
  val RRTYPE_SOA: RRType = "SOA"

  // name authority pointer record
  val RRTYPE_NAPTR: RRType = "NAPTR"

  /**
    * DNS Extensions
    * @param dns the DNS instance
    */
  implicit class DNSExtensions(val dns: DNS) extends AnyVal {

    /**
      * Resolves a hostname (e.g. 'nodejs.org') into the first found A (IPv4) or AAAA (IPv6) record. options can be an
      * object or integer. If options is not provided, then IPv4 and IPv6 addresses are both valid. If options is an
      * integer, then it must be 4 or 6.
      */
    @inline
    def lookupAsync(hostname: String, options: DnsOptions | RawOptions | Int = null): Promise[String] = {
      promiseWithError1[DnsError, String](dns.lookup(hostname, options, _))
    }

    /**
      * Resolves the given address and port into a hostname and service using the operating system's underlying
      * getnameinfo implementation.
      *
      * If address is not a valid IP address, a TypeError will be thrown. The port will be coerced to a number. If it is
      * not a legal port, a TypeError will be thrown.
      *
      * The callback has arguments (err, hostname, service). The hostname and service arguments are strings
      * (e.g. 'localhost' and 'http' respectively).
      *
      * On error, err is an Error object, where err.code is the error code.
      */
    @inline
    def lookupServiceAsync(address: String, port: Int): Promise[(String, String)] = {
      promiseWithError2[DnsError, String, String](dns.lookupService(address, port, _))
    }

    /**
      * Uses the DNS protocol to resolve a hostname (e.g. 'nodejs.org') into an array of the record types specified by rrtype.
      * On error, err is an Error object, where err.code is one of the error codes listed here.
      * @param hostname the hostname
      */
    @inline
    def resolveAsync[T](hostname: String, rrtype: RRType = null): Promise[T] = {
      promiseWithError1[DnsError, T](dns.resolve(hostname, rrtype, _))
    }

    /**
      * Performs a reverse DNS query that resolves an IPv4 or IPv6 address to an array of hostnames.
      * The callback function has arguments (err, hostnames), where hostnames is an array of resolved hostnames for the given ip.
      * On error, err is an Error object, where err.code is one of the DNS error codes.
      * @param ipAddress the IP Address
      */
    @inline
    def reverseAsync(ipAddress: String): Promise[js.Array[String]] = {
      promiseWithError1[DnsError, js.Array[String]](dns.reverse(ipAddress, _))
    }

  }

}

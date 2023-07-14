//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.china.hcg.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.china.hcg.java.set.List;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ZipUtil {
    public ZipUtil() {
    }

    public static String gzip(String primStr) {
        if (primStr != null && primStr.length() != 0) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = null;

            try {
                gzip = new GZIPOutputStream(out);
                gzip.write(primStr.getBytes());
            } catch (IOException var12) {
                var12.printStackTrace();
            } finally {
                if (gzip != null) {
                    try {
                        gzip.close();
                    } catch (IOException var11) {
                        var11.printStackTrace();
                    }
                }

            }

            return (new BASE64Encoder()).encode(out.toByteArray());
        } else {
            return primStr;
        }
    }

    public static String gunzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        } else {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayInputStream in = null;
            GZIPInputStream ginzip = null;
            byte[] compressed = null;
            String decompressed = null;

            try {
                compressed = (new BASE64Decoder()).decodeBuffer(compressedStr);
                in = new ByteArrayInputStream(compressed);
                ginzip = new GZIPInputStream(in);
                byte[] buffer = new byte[1024];
                boolean var7 = true;

                int offset;
                while((offset = ginzip.read(buffer)) != -1) {
                    out.write(buffer, 0, offset);
                }

                decompressed = new String(out.toByteArray(), "gb2312");
            } catch (IOException var24) {
                var24.printStackTrace();
            } finally {
                if (ginzip != null) {
                    try {
                        ginzip.close();
                    } catch (IOException var23) {
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException var22) {
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException var21) {
                    }
                }

            }

            return decompressed;
        }
    }

    public static final String zip(String str) {
        if (str == null) {
            return null;
        } else {
            ByteArrayOutputStream out = null;
            ZipOutputStream zout = null;
            String compressedStr = null;

            try {
                out = new ByteArrayOutputStream();
                zout = new ZipOutputStream(out);
                zout.putNextEntry(new ZipEntry("0"));
                zout.write(str.getBytes());
                zout.closeEntry();
                byte[] compressed = out.toByteArray();
                compressedStr = (new BASE64Encoder()).encodeBuffer(compressed);
            } catch (IOException var18) {
                Object var1 = null;
            } finally {
                if (zout != null) {
                    try {
                        zout.close();
                    } catch (IOException var17) {
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException var16) {
                    }
                }

            }

            return compressedStr;
        }
    }

    public static final String unzip(String compressedStr) {
        if (compressedStr == null) {
            return null;
        } else {
            ByteArrayOutputStream out = null;
            ByteArrayInputStream in = null;
            ZipInputStream zin = null;
            String decompressed = null;

            try {
                byte[] compressed = (new BASE64Decoder()).decodeBuffer(compressedStr);
                out = new ByteArrayOutputStream();
                in = new ByteArrayInputStream(compressed);
                zin = new ZipInputStream(in);
                zin.getNextEntry();
                byte[] buffer = new byte[1024];
                boolean var7 = true;

                int offset;
                while((offset = zin.read(buffer)) != -1) {
                    out.write(buffer, 0, offset);
                }

                decompressed = new String(out.toByteArray(), "gb2312");
            } catch (IOException var24) {
                decompressed = null;
            } finally {
                if (zin != null) {
                    try {
                        zin.close();
                    } catch (IOException var23) {
                    }
                }

                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException var22) {
                    }
                }

                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException var21) {
                    }
                }

            }

            return decompressed;
        }
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String DetailRecords = "商户号|交易类型|订单编号|交易时间|交易金额|商户账号|商户动账金额|客户账号|账户类型|商户回佣手续费|商户分期手续费|会计日期|主机流水号|9014流水号|原订单号^^103883070210088|Sale|18092716492122988318|20180927165428|0.50|30702501040005488|0.50|6228480898492269979|401|0.00|0.00|20180927|461602083|9RECEP01164952172053|^^103883070210088|Sale|18092716545705589284|20180927170017|0.10|30702501040005488|0.10|6228480898492269979|401|0.00|0.00|20180927|464913123|9RECEP01165437896710|^^103883070210088|Sale|18092717011916045316|20180927170606|0.20|30702501040005488|0.20|6228480898492269979|401|0.00|0.00|20180927|468079803|9RECEP01170032221461|^^103883070210088|Sale|18092717025810977622|20180927170733|0.30|30702501040005488|0.30|6228480898492269979|401|0.00|0.00|20180927|468850265|9RECEP01170158015637|^^103883070210088|Sale|18092717173883664167|20180927172250|0.10|30702501040005488|0.10|6228480898492269979|401|0.00|0.00|20180927|476494963|9RECEP01171955369921|";
        String compress = gzip(DetailRecords);
        System.out.println("zip compress:" + compress);
//        System.out.println("zip decompress:" + gunzip("H4sIAAAAAAAEADWOPUsDQRCG/5G8s7O3N1tLeiF9wMLOnzB/R4KFSgovkE1QixwmZyB+3CmpIliLXcTKveWumOGd552v1VMYz5a6CC/v9/vtRovL24vJviPryfwricVP8a2r2FpX0WpFcVNXid5tOlpXYZxWtHb4eC3WzfZ3+pzKaVPuujJ8zsvHutxpcwh/D4fqOo56kO3k21X7wWw5GhFYhJHDECCiw9PzMyWBN0IuhjfETJknNegxsxUlHAGaJjMQLIDMSs+dYWQG4gHDubWcQy1Ik5lSv06tdc6DnagfDo4HJ6B42GcEZ8iy/gPSFXokOwEAAA=="));
//        System.out.println("zip decompress2:" + gunzip("H4sIAAAAAAAEAJVOPUsDQRD9RzKzO7u3W+tZqr8gYGGRRuxUmL8jwUIlhRfIJqhFDpMzED/ulFQRrMUuIZVz5zUBAzrVvPfmvXnDh9DqDrgfnl5vZ5MxJ+fXZ+1ZzYzavY9q6X8lnzyU0zwTqVySqzyr2JtxzeZZaFURpRzenpNRMVl0HivYKdJpDcN7L73P0ykX87C8m2eXYvWAVK8vF2WD7qDRQNDOgS8HFTk+PmieNA+P9k9ZAXog8OBNpDUiAFj0hKsCMWwow+C1RSMCablzBn/ond2teJsJBAoBUHsRWEeERC4iJh9vxnuAEmeVM2Sdp7XP/1IY5R8qtOKJUEW0KmjGX9riv6qiNtLTaglVa99+A4r1M5T0AQAA"));
        String decompress = gunzip("H4sIAAAAAAAEAK2QO0sDQRDHv4+FzGtnd2tNJYhgoxYBCzs/wn4dCRYqKbxALkEtEkxiID4SJZWCtdhFrNy7nHoWYgpnm5n/vH47vau01uyEdnpzf/44Gobk8PSg/lgo/XrrOXfar8lL6MXS8SCmMic5GQ9y9WxYqONBWstHZOn04TbpT0Zvjes8bEy60yJMn1rdy3F3Giaz9P1iNjiOrR5QCvfuKCNodqpVBHYOGQGASGzY3N3fC+urO1sUFVRnyZAQOHBGyBoOBMSgBGiRDQVYBgyxH1GUFOZm57LGyqXcVK0PEiWI73NCMACeldkFXausVDYAs6EAauM4CIvBESEadgwkWIYDxj/gjJ/DkUGJcPITbhslbvdCWqIDiyQKwrQgnZh4NCRla6FMJ1ScDoCzckCnCgb1S8U5HFqV/HJltm8ijj1sHTrC34hKNByXoUMvylKmiQf8FxrDHkz8MXsfPgB2Dzjo8wIAAA==");
        System.err.println(decompress);
    }


}

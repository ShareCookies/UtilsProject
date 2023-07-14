package com.china.hcg.utils.base64;

import java.util.Base64;

/**
 * @autor hecaigui
 * @date 2023/5/4
 * @description
 */
public class test {
    public static void main(String[] args) {
        String encodedString = "PE1TRz48TWVzc2FnZT48VHJ4UmVzcG9uc2U+PFJldHVybkNvZGU+MDAwMDwvUmV0dXJuQ29kZT48RXJyb3JNZXNzYWdlPr270tezybmmPC9FcnJvck1lc3NhZ2U+PEVDTWVyY2hhbnRUeXBlPkVCVVM8L0VDTWVyY2hhbnRUeXBlPjxNZXJjaGFudElEPjEwMzg4MjIwMDAwMDk1ODwvTWVyY2hhbnRJRD48VHJ4VHlwZT5BQkNTb2Z0VG9rZW5QYXk8L1RyeFR5cGU+PE9yZGVyTm8+T04yYTAxNDA5MjQwMDMxMTIxd3c8L09yZGVyTm8+PEFtb3VudD4wLjAxPC9BbW91bnQ+PEJhdGNoTm8+MDAxMzA2PC9CYXRjaE5vPjxWb3VjaGVyTm8+OTM3MjI4PC9Wb3VjaGVyTm8+PEhvc3REYXRlPjIwMjAvMDYvMTg8L0hvc3REYXRlPjxIb3N0VGltZT4xOTozMzoxMzwvSG9zdFRpbWU+PFBheVR5cGU+RVAwNzI8L1BheVR5cGU+PE5vdGlmeVR5cGU+MTwvTm90aWZ5VHlwZT48aVJzcFJlZj42SUVDRVAwMTE3MjkzMTk4OTA0OTwvaVJzcFJlZj48QWNjRGF0ZT4yMDIwMDcyMTwvQWNjRGF0ZT48QWNxRmVlPjAuMDA8L0FjcUZlZT48SXNzRmVlPjAuMDA8L0lzc0ZlZT48SnJuTm8+IDEyMzE3NDAwMjwvSnJuTm8+PC9UcnhSZXNwb25zZT48L01lc3NhZ2U+PFNpZ25hdHVyZS1BbGdvcml0aG0+U0hBMXdpdGhSU0E8L1NpZ25hdHVyZS1BbGdvcml0aG0+PFNpZ25hdHVyZT5PWGFkbXdYS1JoeVQzQWlkU3dvYkdmdmRSSDFYek9PL211dUNIcit1OGZPcHZkSUV6QkV1YWpOQzFoUkZxVm1JbWRDODU4TnhPTit3TzU2bzFjZU9TK1E4SGp5ekxLUUNHSUlVYjcyUHIxM3NobStKT2dvK1ZzMHBCUlk5Q2R0R3Q2N25uY3NUZ3dsQ3hiODVzS054czcrU2wxSVhOVWZRbGE4WEtnZWV4WXM9PC9TaWduYXR1cmU+PC9NU0c+";
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        String decodedString = new String(decodedBytes);
        System.out.println(decodedString); // 输出 "Hello World!"
    }
}

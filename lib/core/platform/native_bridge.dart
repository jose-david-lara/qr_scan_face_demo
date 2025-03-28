import 'package:flutter/services.dart';

class NativeBridge {
  static const _channel = MethodChannel("com.example.qr_scan_face_demo/channel");

  static Future<String> scanQrCode() async {
    final result = await _channel.invokeMethod<String>("scanQrCode");
    return result ?? "";
  }

  static Future<String> sendMessage(String message) async {
    final response = await _channel.invokeMethod("nativeFunction", {
      "message": message,
    });
    return response ?? "Sin respuesta";
  }
}

import 'package:flutter/services.dart';

class ScanStream {
  static const _eventChannel = EventChannel("com.example.qr_scan_face_demo/scanStream");

  static Stream<List<dynamic>> get qrStream =>
      _eventChannel.receiveBroadcastStream().map((event) {
        return (event as List).map((e) {
          final map = Map<String, dynamic>.from(e as Map);
          return {
            'content': map['content'] as String,
            'timestamp': map['timestamp'] as int,
          };
        }).toList();
      });
}

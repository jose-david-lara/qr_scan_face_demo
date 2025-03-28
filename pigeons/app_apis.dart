import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class TestApi {
  String sayHello(String name);
}

@HostApi()
abstract class BiometricApi {
  bool isBiometricAvailable();
  bool authenticate();
}

class QrScanResult {
  String content;
  int timestamp;

  QrScanResult({
    required this.content,
    required this.timestamp,
  });
}

@HostApi()
abstract class QrHistoryApi {
  List<QrScanResult> getAllScans();
}

@HostApi()
abstract class PinApi {
  bool savePin(String pin);
  String? getPin();
  void clearPin();
}
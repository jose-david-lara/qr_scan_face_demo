import 'package:qr_scan_face_demo/core/platform/app_apis.dart';

class FakePinApi extends PinApi {
  String? _pin;

  @override
  Future<bool> savePin(String pin) async {
    _pin = pin;
    return true;
  }

  @override
  Future<String?> getPin() async {
    return _pin;
  }

  @override
  Future<void> clearPin() async {
    _pin = null;
  }
}

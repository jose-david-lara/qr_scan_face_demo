import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class BiometricApi {
  bool isBiometricAvailable();
  bool authenticate();
}



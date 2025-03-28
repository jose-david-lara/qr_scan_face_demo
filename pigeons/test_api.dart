import 'package:pigeon/pigeon.dart';

@HostApi()
abstract class TestApi {
  String sayHello(String name);
}

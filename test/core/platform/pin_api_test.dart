import 'package:flutter_test/flutter_test.dart';
import 'fake_pin_api.dart'; // tu clase de prueba

void main() {
  final pinApi = FakePinApi();

  group('PinApi', () {
    test('guardar y recuperar PIN', () async {
      const testPin = '4321';

      final saved = await pinApi.savePin(testPin);
      expect(saved, true);

      final fetched = await pinApi.getPin();
      expect(fetched, equals(testPin));

      await pinApi.clearPin();
      final cleared = await pinApi.getPin();
      expect(cleared, isNull);
    });
  });
}

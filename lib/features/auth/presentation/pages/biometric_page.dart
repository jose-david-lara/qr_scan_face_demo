import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/core/platform/app_apis.dart';
import 'package:qr_scan_face_demo/features/auth/presentation/pages/pin_fallback_page.dart';

class BiometricPage extends StatefulWidget {
  const BiometricPage({super.key});

  @override
  State<BiometricPage> createState() => _BiometricPageState();
}

class _BiometricPageState extends State<BiometricPage> {
  bool _isAvailable = false;
  String _status = 'Esperando autenticación...';

  @override
  void initState() {
    super.initState();
    _checkBiometricAvailability();
  }

  Future<void> _checkBiometricAvailability() async {
    final biometric = BiometricApi();
    final available = await biometric.isBiometricAvailable();
    setState(() {
      _isAvailable = available;
    });
  }

  Future<void> _startBiometricAuth() async {
    final biometric = BiometricApi();
    final success = await biometric.authenticate();

    if (success) {
      setState(() {
        _status = 'Autenticación biométrica exitosa ';
      });
    } else {
      final result = await Navigator.push<bool>(
        context,
        MaterialPageRoute(builder: (_) => const PinFallbackPage()),
      );

      if (result == true) {
        setState(() {
          _status = 'PIN aceptado ';
        });
      } else {
        setState(() {
          _status = 'Autenticación fallida ';
        });
      }
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Biometría")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(_status, style: const TextStyle(fontSize: 18)),
            const SizedBox(height: 20),
            _isAvailable
                ? ElevatedButton(
              onPressed: _startBiometricAuth,
              child: const Text("Iniciar sesión con biometría"),
            )
                : const Text("Biometría no disponible en este dispositivo"),
          ],
        ),
      ),
    );
  }
}

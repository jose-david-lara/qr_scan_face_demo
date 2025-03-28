import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/core/platform/app_apis.dart';

class PinFallbackPage extends StatefulWidget {
  const PinFallbackPage({super.key});

  @override
  State<PinFallbackPage> createState() => _PinFallbackPageState();
}

class _PinFallbackPageState extends State<PinFallbackPage> {
  final TextEditingController _pinController = TextEditingController();
  String _error = '';

  final PinApi _pinApi = PinApi();
  String? _validPin;

  void _validatePin() {
    if (_validPin == null) {
      setState(() {
        _error = 'PIN no configurado';
      });
      return;
    }

    if (_pinController.text == _validPin) {
      Navigator.pop(context, true); // éxito
    } else {
      setState(() {
        _error = 'PIN incorrecto';
      });
    }
  }

  @override
  void initState() {
    super.initState();
    _loadSavedPin();
  }

  Future<void> _loadSavedPin() async {
    final savedPin = await _pinApi.getPin();
    setState(() {
      _validPin = savedPin;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("PIN de respaldo")),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Text("Ingresa tu PIN de respaldo"),
            const SizedBox(height: 12),
            TextField(
              controller: _pinController,
              keyboardType: TextInputType.number,
              obscureText: true,
              decoration: const InputDecoration(
                border: OutlineInputBorder(),
                hintText: 'PIN',
              ),
            ),
            const SizedBox(height: 12),
            if (_error.isNotEmpty) Text(_error, style: const TextStyle(color: Colors.red)),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: _validatePin,
              child: const Text("Validar"),
            )
          ],
        ),
      ),
    );
  }
}

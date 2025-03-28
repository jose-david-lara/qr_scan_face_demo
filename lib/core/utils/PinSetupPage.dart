import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/core/platform/app_apis.dart';

class PinSetupPage extends StatelessWidget {
  final TextEditingController _controller = TextEditingController();
  final PinApi _pinApi = PinApi();

  PinSetupPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Configurar PIN")),
      body: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          children: [
            const Text("Ingresa un PIN"),
            const SizedBox(height: 8),
            TextField(
              controller: _controller,
              keyboardType: TextInputType.number,
              obscureText: true,
              decoration: const InputDecoration(border: OutlineInputBorder()),
            ),
            const SizedBox(height: 16),
            ElevatedButton(
              onPressed: () async {
                await _pinApi.savePin(_controller.text);
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("PIN guardado")),
                );
                Navigator.pop(context);
              },
              child: const Text("Guardar PIN"),
            )
          ],
        ),
      ),
    );
  }
}

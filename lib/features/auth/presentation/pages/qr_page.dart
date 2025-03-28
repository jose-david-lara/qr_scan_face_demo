import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/core/platform/native_bridge.dart';

class QrPage extends StatefulWidget {
  const QrPage({super.key});

  @override
  State<QrPage> createState() => _QrPageState();
}

class _QrPageState extends State<QrPage> {
  String _result = "Esperando escaneo...";

  Future<void> _scanQr() async {
    final result = await NativeBridge.scanQrCode();
    setState(() {
      _result = result.isNotEmpty ? "QR escaneado: $result" : "No se encontró QR";
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Escáner QR")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(_result),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () async {
                final result = await NativeBridge.scanQrCode();
                print("Resultado: $result");
              },
              child: const Text("Escanear QR"),
            ),
          ],
        ),
      ),
    );
  }
}

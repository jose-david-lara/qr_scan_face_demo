import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/features/home/presentation/widgets/home_options_card.dart';

class HomePage extends StatelessWidget {
  final VoidCallback onBiometric;
  final VoidCallback onQr;

  const HomePage({
    super.key,
    required this.onBiometric,
    required this.onQr,
  });

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Demo QR & Biometría")),
      body: Center(
        child: HomeOptionsCard(
          onBiometricTap: onBiometric,
          onQrScanTap: onQr,
        ),
      ),
    );
  }
}

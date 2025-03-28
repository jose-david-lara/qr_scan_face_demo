import 'package:flutter/material.dart';
import 'package:qr_scan_face_demo/features/home/presentation/pages/home_page.dart';
import 'package:qr_scan_face_demo/features/auth/presentation/pages/biometric_page.dart';
import 'package:qr_scan_face_demo/core/platform/app_apis.dart';

import 'core/platform/native_bridge.dart';

final GlobalKey<NavigatorState> navigatorKey = GlobalKey<NavigatorState>();

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'QR & Biometría Demo',
      debugShowCheckedModeBanner: false,
      navigatorKey: navigatorKey,
      theme: ThemeData(
        useMaterial3: true,
        colorSchemeSeed: Colors.teal,
      ),
      initialRoute: '/',
      routes: {
        '/': (_) => HomePage(
          onBiometric: () {
            navigatorKey.currentState?.pushNamed('/biometric');
          },
          onQr: () async {
            final result = await NativeBridge.scanQrCode();
            if (result.isNotEmpty) {
              ScaffoldMessenger.of(navigatorKey.currentContext!).showSnackBar(
                SnackBar(content: Text("QR escaneado: $result")),
              );
            }
          },
        ),
        '/biometric': (_) => const BiometricPage(),
      },
    );
  }
}

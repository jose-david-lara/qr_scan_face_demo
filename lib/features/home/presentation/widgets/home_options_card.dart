import 'package:flutter/material.dart';

class HomeOptionsCard extends StatelessWidget {
  final VoidCallback onBiometricTap;
  final VoidCallback onQrScanTap;

  const HomeOptionsCard({
    super.key,
    required this.onBiometricTap,
    required this.onQrScanTap,
  });

  @override
  Widget build(BuildContext context) {
    final isDark = Theme.of(context).brightness == Brightness.dark;

    return Padding(
      padding: const EdgeInsets.all(24),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          _buildOptionCard(
            icon: Icons.fingerprint,
            label: "Autenticación biométrica",
            color: Colors.teal,
            onTap: onBiometricTap,
          ),
          const SizedBox(height: 24),
          _buildOptionCard(
            icon: Icons.qr_code_scanner,
            label: "Escanear código QR",
            color: Colors.indigo,
            onTap: onQrScanTap,
          ),
        ],
      ),
    );
  }

  Widget _buildOptionCard({
    required IconData icon,
    required String label,
    required Color color,
    required VoidCallback onTap,
  }) {
    return InkWell(
      onTap: onTap,
      borderRadius: BorderRadius.circular(16),
      child: Ink(
        decoration: BoxDecoration(
          color: color.withOpacity(0.1),
          borderRadius: BorderRadius.circular(16),
          border: Border.all(color: color, width: 1.5),
        ),
        child: Padding(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 20),
          child: Row(
            children: [
              Icon(icon, size: 32, color: color),
              const SizedBox(width: 16),
              Expanded(
                child: Text(
                  label,
                  style: TextStyle(
                    fontSize: 18,
                    color: color,
                    fontWeight: FontWeight.w600,
                  ),
                ),
              ),
              const Icon(Icons.arrow_forward_ios_rounded, size: 18)
            ],
          ),
        ),
      ),
    );
  }
}

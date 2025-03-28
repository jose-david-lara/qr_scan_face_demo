import 'package:flutter/material.dart';
import 'package:intl/intl.dart';
import 'package:qr_scan_face_demo/core/platform/scan_stream.dart';

class QrHistoryPage extends StatelessWidget {
  const QrHistoryPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Historial QR (Tiempo real)")),
      body: StreamBuilder<List<dynamic>>(
        stream: ScanStream.qrStream,
        builder: (context, snapshot) {
          final scans = snapshot.data ?? [];

          if (scans.isEmpty) {
            return const Center(child: Text("Sin escaneos aún"));
          }

          return ListView.builder(
            itemCount: scans.length,
            itemBuilder: (_, index) {
              final scan = scans[index];
              final date = DateFormat.yMd().add_jm().format(
                DateTime.fromMillisecondsSinceEpoch(scan['timestamp']),
              );
              return ListTile(
                title: Text(scan['content']),
                subtitle: Text(date),
              );
            },
          );
        },
      ),
    );
  }
}

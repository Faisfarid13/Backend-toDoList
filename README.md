API Endpoints:<br>
Authentication

POST /api/auth/register - Mendaftarkan pengguna baru<br>
POST /api/auth/login - Login dan menerima token JWT

Checklists

GET /api/checklists - Mendapatkan semua daftar periksa untuk pengguna saat ini<br>
GET /api/checklists/{id} - Mendapatkan daftar periksa spesifik berdasarkan ID <br>
POST /api/checklists - Membuat daftar periksa baru <br>
DELETE /api/checklists/{id} - Menghapus daftar periksa<br>

Items

GET /api/checklists/{checklistId}/items - Mendapatkan semua item dalam daftar periksa <br>
GET /api/checklists/{checklistId}/items/{itemId} - Mendapatkan item tertentu <br>
POST /api/checklists/{checklistId}/items - Membuat item baru dalam daftar periksa<br> 
PUT /api/checklists/{checklistId}/items/{itemId} - Memperbarui item <br>
PATCH /api/checklists/{checklistId}/items/{itemId}/status - Memperbarui status penyelesaian item <br>
DELETE /api/checklists/{checklistId}/items/{itemId} - Menghapus item<br>

Security

Semua endpoint kecuali /api/auth/** memerlukan autentikasi<br>
Token JWT divalidasi untuk setiap permintaan<br>
Data pengguna dipisahkan (pengguna hanya dapat mengakses data mereka sendiri)<br>

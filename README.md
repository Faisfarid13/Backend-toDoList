API Endpoints:
Authentication

POST /api/auth/register - Mendaftarkan pengguna baru 
POST /api/auth/login - Login dan menerima token JWT

Checklists

GET /api/checklists - Mendapatkan semua daftar periksa untuk pengguna saat ini 
GET /api/checklists/{id} - Mendapatkan daftar periksa spesifik berdasarkan ID 
POST /api/checklists - Membuat daftar periksa baru 
DELETE /api/checklists/{id} - Menghapus daftar periksa

Items

GET /api/checklists/{checklistId}/items - Mendapatkan semua item dalam daftar periksa 
GET /api/checklists/{checklistId}/items/{itemId} - Mendapatkan item tertentu 
POST /api/checklists/{checklistId}/items - Membuat item baru dalam daftar periksa 
PUT /api/checklists/{checklistId}/items/{itemId} - Memperbarui item 
PATCH /api/checklists/{checklistId}/items/{itemId}/status - Memperbarui status penyelesaian item 
DELETE /api/checklists/{checklistId}/items/{itemId} - Menghapus item

Security:

Semua endpoint kecuali /api/auth/** memerlukan autentikasi

Token JWT divalidasi untuk setiap permintaan

Data pengguna dipisahkan (pengguna hanya dapat mengakses data mereka sendiri)

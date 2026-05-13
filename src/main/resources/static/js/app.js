// ============ ГАМБУРГЕР-МЕНЮ ДЛЯ МОБИЛЬНЫХ ============
document.addEventListener('DOMContentLoaded', function() {
    const hamburger = document.querySelector('.hamburger');
    const sidebar = document.querySelector('.sidebar');
    const overlay = document.querySelector('.sidebar-overlay');
    
    if (hamburger) {
        hamburger.addEventListener('click', function() {
            sidebar.classList.toggle('active');
            if (overlay) overlay.classList.toggle('active');
        });
    }
    
    // Закрытие меню при клике на оверлей
    if (overlay) {
        overlay.addEventListener('click', function() {
            sidebar.classList.remove('active');
            overlay.classList.remove('active');
        });
    }
    
    // Закрытие меню при клике на ссылку (мобильные)
    const sidebarLinks = document.querySelectorAll('.sidebar a');
    sidebarLinks.forEach(link => {
        link.addEventListener('click', function() {
            if (window.innerWidth <= 768) {
                sidebar.classList.remove('active');
                if (overlay) overlay.classList.remove('active');
            }
        });
    });
});

// ============ МОДАЛЬНЫЕ ОКНА ============
function openModal(modalId) {
    document.getElementById(modalId).classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// Закрытие по клику вне модалки
document.addEventListener('click', function(e) {
    if (e.target.classList.contains('modal-overlay')) {
        e.target.classList.remove('active');
    }
});

// ============ QR-СКАНЕР (веб-камера) ============
let scannerActive = false;
let qrScanner = null;

function startQrScanner(inputId) {
    const scannerArea = document.getElementById('qr-scanner-area');
    const video = document.getElementById('qr-video');
    
    if (!scannerActive) {
        scannerActive = true;
        scannerArea.style.display = 'block';
        
        // Используем библиотеку jsQR или аналогичную
        navigator.mediaDevices.getUserMedia({ video: { facingMode: "environment" } })
            .then(function(stream) {
                video.srcObject = stream;
                video.setAttribute("playsinline", true);
                video.play();
                requestAnimationFrame(tick);
            })
            .catch(function(err) {
                alert('Ошибка доступа к камере: ' + err.message);
            });
    }
    
    function tick() {
        if (video.readyState === video.HAVE_ENOUGH_DATA && scannerActive) {
            const canvas = document.createElement('canvas');
            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;
            canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
            
            const imageData = canvas.getContext('2d').getImageData(0, 0, canvas.width, canvas.height);
            const code = jsQR(imageData.data, imageData.width, imageData.height);
            
            if (code) {
                document.getElementById(inputId).value = code.data;
                stopQrScanner();
                return;
            }
        }
        if (scannerActive) {
            requestAnimationFrame(tick);
        }
    }
}

function stopQrScanner() {
    scannerActive = false;
    const video = document.getElementById('qr-video');
    if (video && video.srcObject) {
        video.srcObject.getTracks().forEach(track => track.stop());
    }
    document.getElementById('qr-scanner-area').style.display = 'none';
}

// ============ ПОДТВЕРЖДЕНИЕ ДЕЙСТВИЙ ============
function confirmAction(message, callback) {
    if (confirm(message)) {
        callback();
    }
}

// ============ АВТООБНОВЛЕНИЕ ДАННЫХ ============
function autoRefresh(seconds, url, targetId) {
    setInterval(function() {
        fetch(url)
            .then(response => response.text())
            .then(html => {
                document.getElementById(targetId).innerHTML = html;
            })
            .catch(err => console.log('Ошибка обновления:', err));
    }, seconds * 1000);
}

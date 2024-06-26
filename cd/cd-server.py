from flask import Flask, request, abort
import subprocess
import hmac
import hashlib

app = Flask(__name__)

@app.route('/webhook', methods=['POST'])
def webhook():
    if request.method == 'POST':
        subprocess.run(['./deploy.sh'], capture_output=True, text=True)
    else:
        abort(405)
if __name__ == '__main__':
    app.run(debug=True, port=3000)

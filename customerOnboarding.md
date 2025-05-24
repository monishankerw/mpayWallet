Customer Onboarding Process Flow
1. Initiate Onboarding
   📱 User enters mobile number/email → API: POST /initiate-onboarding
   📩 OTP sent via SMS/email.

2. Verify OTP
   🔢 User enters OTP → API: POST /verify-otp
   ✅ Success: Proceed to registration.
   ❌ Fail: Retry OTP (max 3 attempts).

3. Collect User Details
   📝 Form with:

Personal Info (name, address)

KYC Documents (PAN, Aadhaar upload)

Bank Details (account, IFSC)
API: POST /submit-details

4. KYC Verification
   🔍 Automated Checks:

PAN validation

Aadhaar authentication

Liveness check (selfie vs document)
🔄 Status: Pending → Approved/Rejected.
📤 API: GET /kyc-status

5. Bank Account Linking
   🏦 Account verification via:

Micro-deposit validation

UPI-based linking
🔗 API: POST /link-bank-account

6. Wallet Activation
   💰 Create wallet with limits:

Partial KYC: ₹10,000/month

Full KYC: ₹2,00,000/month
🎉 API: POST /activate-wallet

7. Confirmation & Access
   📬 Email/SMS confirmation sent.
   🔑 User gains full wallet access.


package com.kmutnb.trainkmutnb67.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kmutnb.trainkmutnb67.data.AppState
import com.kmutnb.trainkmutnb67.data.PassengerType
import com.kmutnb.trainkmutnb67.i18n.LocalStrings
import com.kmutnb.trainkmutnb67.ui.components.AppTextField
import com.kmutnb.trainkmutnb67.ui.components.GradientButton
import com.kmutnb.trainkmutnb67.ui.components.SecondaryButton
import com.kmutnb.trainkmutnb67.ui.components.SegmentedTabs
import com.kmutnb.trainkmutnb67.ui.theme.BgDark
import com.kmutnb.trainkmutnb67.ui.theme.BrandGradient
import com.kmutnb.trainkmutnb67.ui.theme.BrandTeal
import com.kmutnb.trainkmutnb67.ui.theme.CardBorder
import com.kmutnb.trainkmutnb67.ui.theme.Danger
import com.kmutnb.trainkmutnb67.ui.theme.Surface1
import com.kmutnb.trainkmutnb67.ui.theme.TextPrimary
import com.kmutnb.trainkmutnb67.ui.theme.TextSecondary

@Composable
fun AuthScreen() {
    val s = LocalStrings.current
    var tab by remember { mutableStateOf(0) }
    var error by remember { mutableStateOf<String?>(null) }
    var showForgot by remember { mutableStateOf(false) }

    fun mapErr(e: AppState.AuthError): String = when (e) {
        AppState.AuthError.INVALID -> s.invalidCredentials
        AppState.AuthError.FIELDS -> s.fillAllFields
        AppState.AuthError.MISMATCH -> s.passwordMismatch
        AppState.AuthError.EMAIL_TAKEN -> s.emailTaken
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(BgDark)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(24.dp))
            Box(
                Modifier.size(64.dp).clip(RoundedCornerShape(16.dp)).background(BrandGradient),
                contentAlignment = Alignment.Center,
            ) { Text("🚆", fontSize = 30.sp) }
            Spacer(Modifier.height(12.dp))
            Text(s.appName, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Text(s.tagline, color = TextSecondary, fontSize = 13.sp)
            Spacer(Modifier.height(24.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Surface1)
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                SegmentedTabs(
                    options = listOf(s.signIn, s.signUp),
                    selectedIndex = tab,
                    onSelect = { tab = it; error = null },
                )

                if (error != null) {
                    Text(error!!, color = Danger, fontSize = 13.sp)
                }

                if (tab == 0) {
                    LoginForm(
                        onSubmit = { email, pw ->
                            when (val r = AppState.login(email, pw)) {
                                is AppState.AuthResult.Error -> error = mapErr(r.messageKey)
                                else -> {}
                            }
                        },
                        onDemo = { AppState.loginDemo() },
                        onForgot = { showForgot = true },
                    )
                } else {
                    RegisterForm(
                        onSubmit = { name, phone, type, email, pw, confirm ->
                            when (val r = AppState.register(name, phone, type, email, pw, confirm)) {
                                is AppState.AuthResult.Error -> error = mapErr(r.messageKey)
                                else -> {}
                            }
                        },
                    )
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }

    if (showForgot) ForgotPasswordDialog(onDismiss = { showForgot = false })
}

@Composable
private fun LoginForm(
    onSubmit: (String, String) -> Unit,
    onDemo: () -> Unit,
    onForgot: () -> Unit,
) {
    val s = LocalStrings.current
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }

    AppTextField(email, { email = it }, s.email, placeholder = "you@example.com")
    AppTextField(pw, { pw = it }, s.password, placeholder = "••••••••", isPassword = true)
    GradientButton(s.signIn, onClick = { onSubmit(email, pw) })
    SecondaryButton(s.useDemo, onClick = onDemo)
    TextButton(onClick = onForgot, modifier = Modifier.fillMaxWidth()) {
        Text(s.forgotPassword, color = BrandTeal, fontSize = 13.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterForm(
    onSubmit: (String, String, PassengerType, String, String, String) -> Unit,
) {
    val s = LocalStrings.current
    val lang = LocalStrings.current.lang
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(PassengerType.GENERAL) }
    var email by remember { mutableStateOf("") }
    var pw by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    AppTextField(name, { name = it }, s.fullName, placeholder = s.fullNameHint)
    AppTextField(phone, { phone = it }, s.phone, placeholder = "0812345678", keyboardNumber = true)

    Column {
        Text(s.passengerType, color = TextSecondary, fontSize = 13.sp)
        Spacer(Modifier.height(6.dp))
        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = it }) {
            OutlinedTextField(
                value = type.label(lang),
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            )
            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                PassengerType.entries.forEach { pt ->
                    DropdownMenuItem(
                        text = { Text(pt.label(lang)) },
                        onClick = { type = pt; expanded = false },
                    )
                }
            }
        }
    }

    AppTextField(email, { email = it }, s.email, placeholder = "you@example.com")
    AppTextField(pw, { pw = it }, s.password, placeholder = "••••••••", isPassword = true)
    AppTextField(confirm, { confirm = it }, s.confirmPassword, placeholder = "••••••••", isPassword = true)
    GradientButton(s.signUp, onClick = { onSubmit(name, phone, type, email, pw, confirm) })
}

@Composable
private fun ForgotPasswordDialog(onDismiss: () -> Unit) {
    val s = LocalStrings.current
    var email by remember { mutableStateOf("") }
    var sent by remember { mutableStateOf(false) }
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = { if (email.isNotBlank()) sent = true else onDismiss() }) {
                Text(if (sent) s.close else s.resetPassword, color = BrandTeal)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text(s.cancel, color = TextSecondary) } },
        title = { Text(s.resetPassword, color = TextPrimary) },
        text = {
            Column {
                Text(if (sent) s.resetPasswordSent else s.resetPasswordDesc, color = TextSecondary, fontSize = 13.sp)
                if (!sent) {
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("you@example.com") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        },
        containerColor = Surface1,
    )
}

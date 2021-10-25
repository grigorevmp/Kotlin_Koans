fun sendMessageToClient(
        client: Client?, message: String?, mailer: Mailer
) {
    if (client == null || message == null)
    	return
    val personalInfo = client.personalInfo
    val email = personalInfo?.email
    email?.let{it ->
        mailer.sendMessage(it, message)
    }
}

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)
interface Mailer {
    fun sendMessage(email: String, message: String)
}
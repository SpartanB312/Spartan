package net.spartanb312.render.features.common

import net.spartanb312.render.features.manager.MessageManager
import net.spartanb312.render.features.manager.MessageManager.DELETE_ID

interface Notifier {

    fun info(message: String) = MessageManager.printInfo(message)
    fun warn(message: String) = MessageManager.printWarning(message)
    fun error(message: String) = MessageManager.printError(message)
    fun debug(message: String) = MessageManager.printDebug(message)

    fun infoNS(message: String, deleteId: Int = DELETE_ID) = MessageManager.printNoSpamInfo(message)
    fun warnNS(message: String, deleteId: Int = DELETE_ID) = MessageManager.printNoSpamWarning(message)
    fun errorNS(message: String, deleteId: Int = DELETE_ID) = MessageManager.printNoSpamError(message)
    fun debugNS(message: String, deleteId: Int = DELETE_ID) = MessageManager.printNoSpamDebug(message)

}
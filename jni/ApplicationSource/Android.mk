LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Application

VENDORS_PATH = $(LOCAL_PATH)/../../../../../vendors
GS2D_SOURCE_RELATIVE_PATH = ../../../../../src
ENGINE_PATH = ../../../../../../engine
ADDONS_PATH = ../../../../../../addons
ENGINE_VENDORS_PATH = ../../../../../../vendors

LOCAL_C_INCLUDES := \
	$(VENDORS_PATH)/BoostSDK/ \
	$(VENDORS_PATH)/libzip/ \
	$(VENDORS_PATH)/soil/

LOCAL_STATIC_LIBRARIES := libzip libpng libsoil libgs2d libangelscript

LOCAL_CFLAGS    := -Werror -DANDROID=1 -DGLES2=1

LOCAL_DEFAULT_CPP_EXTENSION := cpp 

LOCAL_SRC_FILES := \
	../../../../../src/Platform/android/main.cpp \
	$(GS2D_SOURCE_RELATIVE_PATH)/Platform/Platform.cpp \
	$(GS2D_SOURCE_RELATIVE_PATH)/Platform/android/Platform.android.cpp \
	$(GS2D_SOURCE_RELATIVE_PATH)/Platform/android/ZipFileManager.cpp \
	$(GS2D_SOURCE_RELATIVE_PATH)/../vendors/soil/SOIL.c \
	$(ENGINE_VENDORS_PATH)/tinyxml_ansi/tinyxml.cpp \
	$(ENGINE_VENDORS_PATH)/tinyxml_ansi/tinystr.cpp \
	$(ENGINE_VENDORS_PATH)/tinyxml_ansi/tinyxmlerror.cpp \
	$(ENGINE_VENDORS_PATH)/tinyxml_ansi/tinyxmlparser.cpp \
	$(ENGINE_PATH)/ETHEntityProperties.cpp \
	$(ENGINE_PATH)/ETHCommon.cpp \
	$(ENGINE_PATH)/ETHCustomDataManager.cpp \
	$(ENGINE_PATH)/ETHLight.cpp \
	$(ENGINE_PATH)/ETHParticleManager.cpp \
	$(ENGINE_PATH)/ETHResourceManager.cpp \
	$(ENGINE_PATH)/ETHResourceProvider.cpp \
	$(ENGINE_PATH)/ETHDirectories.cpp \
	$(ENGINE_PATH)/ETHSpeedTimer.cpp \
	$(ADDONS_PATH)/ansi/scriptstdstring.cpp \
	$(ADDONS_PATH)/ansi/scriptbuilder.cpp \
	$(ADDONS_PATH)/ansi/scriptfile.cpp \
	$(ADDONS_PATH)/scriptarray.cpp \
	$(ADDONS_PATH)/scriptmath.cpp \
	$(ADDONS_PATH)/scriptmath2d.cpp \
	$(ADDONS_PATH)/scriptmath3d.cpp \
	$(ADDONS_PATH)/scriptdictionary.cpp \
	$(ENGINE_PATH)/ETHBucketManager.cpp \
	$(ENGINE_PATH)/ETHShaders.glsl.cpp \
	$(ENGINE_PATH)/ETHScriptEntity.cpp \
	$(ENGINE_PATH)/ETHRenderEntity.cpp \
	$(ENGINE_PATH)/ETHSpriteEntity.cpp \
	$(ENGINE_PATH)/ETHEntityArray.cpp \
	$(ENGINE_PATH)/ETHEntity.cpp \
	$(ENGINE_PATH)/ETHEntityController.cpp \
	$(ENGINE_PATH)/ETHParallaxManager.cpp \
	$(ENGINE_PATH)/ETHShaderManager.cpp \
	$(ENGINE_PATH)/ETHVertexLightDiffuse.cpp \
	$(ENGINE_PATH)/ETHPixelLightDiffuseSpecular.cpp \
	$(ENGINE_PATH)/ETHFakeEyePositionManager.cpp \
	$(ENGINE_PATH)/ETHASUtil.cpp \
	$(ENGINE_PATH)/ETHScene.cpp \
	$(ENGINE_PATH)/ETHTempEntityHandler.cpp \
	$(ENGINE_PATH)/ETHSceneProperties.cpp \
	$(ENGINE_PATH)/ETHLightmapGen.cpp \
	$(ENGINE_PATH)/ETHScriptWrapper.cpp \
	$(ENGINE_PATH)/ETHScriptWrapper.Math.cpp \
	$(ENGINE_PATH)/ETHScriptWrapper.Math.generic.cpp \
	$(ENGINE_PATH)/ETHScriptWrapper.generic.cpp \
	$(ENGINE_PATH)/ETHScriptObjRegister.cpp \
	$(ENGINE_PATH)/ETHScriptObjRegister.generic.cpp \
	$(ENGINE_PATH)/ETHPrimitiveDrawer.cpp \
	$(ENGINE_PATH)/ETHDateTime.cpp \
	$(ENGINE_PATH)/ETHInput.cpp \
	$(ENGINE_PATH)/ETHEngine.cpp \
	$(ENGINE_PATH)/ETHBinaryStream.cpp \
	$(ENGINE_PATH)/ETHEntityCache.cpp \


# REMOVE ApplicationTemplate.cpp AND ENTER YOUR SOURCE FILES HERE:
# LOCAL_SRC_FILES += ApplicationTemplate.cpp

LOCAL_LDLIBS := -ldl -llog -lGLESv2 -lz

include $(BUILD_SHARED_LIBRARY)
# include $(BUILD_STATIC_LIBRARY)

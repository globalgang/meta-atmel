DESCRIPTION = "Microchip EGT Theroststat Demo Application"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;endline=202;md5=3b83ef96387f14655fc854ddc3c6bd57"

PACKAGES = "\
    ${PN} \
    ${PN}-dev \
    ${PN}-dbg \
"
DEPENDS = " libegt"

SRC_URI = "gitsm://github.com/linux4sam/egt-samples.git;protocol=https \
	   file://0001-fix-the-builf-error-of-Cannot-use-CP_USE_DOUBLES-on-.patch "

PV = "1.1+git${SRCPV}"
SRCREV = "a05f393d1cffba155cf5260639ec1a40801b1935"

S = "${WORKDIR}/git"

inherit pkgconfig autotools gettext

do_configure_prepend() {
     ( cd ${S}; ${S}/autogen.sh; cd -)
}

FILES_${PN} += " \
    /usr/share/egt/* \
"
# out-of-tree building doesn't appear to work for this package.
B = "${S}"

EXTRA_OECONF = "--program-prefix='egt_'"

python __anonymous () {
    endianness = d.getVar('SITEINFO_ENDIANNESS')
    if endianness == 'be':
        raise bb.parse.SkipRecipe('Requires little-endian target.')
}

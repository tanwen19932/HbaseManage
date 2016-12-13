package com.mininglamp.nlp.languageDetect;

import com.google.common.base.Optional;
import com.optimaize.langdetect.LanguageDetector;
import com.optimaize.langdetect.LanguageDetectorBuilder;
import com.optimaize.langdetect.i18n.LdLocale;
import com.optimaize.langdetect.ngram.NgramExtractors;
import com.optimaize.langdetect.profiles.LanguageProfile;
import com.optimaize.langdetect.profiles.LanguageProfileReader;
import com.optimaize.langdetect.text.CommonTextObjectFactories;
import com.optimaize.langdetect.text.TextObject;
import com.optimaize.langdetect.text.TextObjectFactory;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by mxf on 16/11/1.
 */
public class LanguageDetect {
	private List<LanguageProfile> languageProfiles = null;
	private LanguageDetector languageDetector = null;
	private TextObjectFactory textObjectFactory = null;
	private Logger logger = Logger.getLogger("LanguageDetect");

	public LanguageDetect() {
		try {
			this.languageProfiles = (new LanguageProfileReader()).readAllBuiltIn();
			this.languageDetector = LanguageDetectorBuilder.create(NgramExtractors.standard()).withProfiles(this.languageProfiles).build();
			if (this.languageDetector == null)
				logger.info("not init detector");
			else
				logger.info("init detector");
			this.textObjectFactory = CommonTextObjectFactories.forIndexingCleanText();
			if (this.textObjectFactory == null)
				logger.info("not init factory");
			else
				logger.info("init factory");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String langDetect(String content) {
		try {
			TextObject e = this.textObjectFactory.forText(content);
			Optional<LdLocale> lang = this.languageDetector.detect(e);
			String langCode = null;
			if(lang.isPresent()) {
				langCode = lang.get().toString();
				if(langCode.equalsIgnoreCase("zh-cn")) {
					langCode = "zh";
				}
			} else {
				content = content.replaceAll("[0-9a-zA-Z\\.\\(\"\\)/,\']", "");
				e = this.textObjectFactory.forText(content);
				lang = this.languageDetector.detect(e);
				if(lang.isPresent()) {
					langCode = lang.get().toString();
					if(langCode.equalsIgnoreCase("zh-cn")) {
						langCode = "zh";
					}
				}
			}
			return langCode;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "zh";
	}


}

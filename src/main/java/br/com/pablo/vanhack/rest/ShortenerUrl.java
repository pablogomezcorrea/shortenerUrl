package br.com.pablo.vanhack.rest;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pablo.vanhack.model.ShortUrl;

@RestController
public class ShortenerUrl {

	private static final char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	private static final String nameCompany = "localhost:8080";
	ShortUrl su = new ShortUrl(); 

	@GetMapping("/{hash}")
	public void redirectToUrl(@PathVariable("hash") String hash, HttpServletResponse resp) throws IOException {
		String url = su.getUrl();
		if (url != null) {
            resp.addHeader("Location", url);
            resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}
	
	@PostMapping("/form") 
	public ModelMap shortPost(@RequestParam("url") String url) throws NoSuchAlgorithmException {
		
		ModelMap mp = new ModelMap();
		String urlMin = null;
		MessageDigest md = MessageDigest.getInstance("MD5");
		if (isCheckUrl(url)) {
			su.setUrl(url);
			md.update(url.getBytes());
			su.setUrlMin(urlMin);
			su.setHash(convertToHex(md.digest()));
			urlMin = su.getHash().substring(0, 5+1);
			
			if ((urlMin != null) && urlMin.length() > 5) {
				mp.addAttribute("urlOri", su.getUrl());
				mp.addAttribute("urlMin", "http://" + nameCompany + "/" + urlMin);
			}
		} 
		return mp;
	}
	
	public String convertToHex(byte[] bytes) {
		StringBuffer sb = new StringBuffer(bytes.length *2);
	    for(final byte b : bytes) {
	        sb.append(hex[b & 0x0F]);
	    }		
		return sb.toString();
	}
	
	public boolean isCheckUrl(String url) {
		boolean resp=true;
		System.out.println(ResourceUtils.isUrl(url));
		if (!ResourceUtils.isUrl(url)) {
			resp=false;
		}
		return resp;
	}
}

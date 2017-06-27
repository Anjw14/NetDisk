package Utils;

/**
 *  FTP�ļ���JavaBean��
 */
public class FtpFile implements FileInterface {
	private String name = ""; // �ļ�����
	private String path = ""; // ·��
	protected boolean directory; // �Ƿ��ļ���
	private boolean file; // �Ƿ��ļ�
	private String lastDate; // ����޸�����
	private String size; // �ļ���С
	private long longSize; // �ļ���С�ĳ���������
	private final int GB = (int) Math.pow(1024, 3); // GB��λ��ֵ
	private final int MB = (int) Math.pow(1024, 2); // MB��λ��ֵ
	private final int KB = 1024; // KB��λ��ֵ

	/**
	 * Ĭ�ϵĹ��췽��
	 */
	public FtpFile() {
	}

	/**
	 * �Զ���Ĺ��췽��
	 * 
	 * @param name
	 *            �ļ���
	 * @param path
	 *            ·��
	 * @param directory
	 *            �Ƿ��ļ���
	 */
	public FtpFile(String name, String path, boolean directory) {
		this.name = name; // ��ʼ���������
		this.path = path;
		this.directory = directory;
	}

	public String getSize() {
		return size;
	}

	/**
	 * �����ļ���С�ķ���
	 * 
	 * @param nsize
	 *            �ļ���С���ַ�����ʾ
	 */
	public void setSize(String nsize) {
		if (nsize.indexOf("DIR") != -1) { // ����ļ���С����DIR�ַ���
			this.size = "<DIR>";
			directory = true; // ���ø����Ϊһ���ļ��ж���
			file = false;
		} else { // ����
			file = true; // ���ø����Ϊһ���ļ�����
			directory = false;
			this.size = nsize.trim(); // �����ļ��Ĵ�С��λ
			longSize = Long.parseLong(size);
			if (longSize > GB) {
				size = longSize / GB + "G ";
			}
			if (longSize > MB) {
				size = longSize / MB + "M ";
			}
			if (longSize > KB) {
				size = longSize / KB + "K ";
			}
		}
	}

	public String getLastDate() {
		return lastDate;
	}

	public void setLastDate(String lastDate) {
		this.lastDate = lastDate;
	}

	public boolean isFile() {
		return file;
	}

	public boolean isDirectory() {
		return directory;
	}

	/**
	 * ��ø��ļ��ľ���·��
	 * 
	 * @return ·��
	 */
	public String getAbsolutePath() {
		if (path.lastIndexOf('/') == path.length() - 1)
			return path + name;
		else
			return path + "/" + name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ��дtoString�������������˸������������ʾ������
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	public long getLongSize() {
		return longSize;
	}
}
